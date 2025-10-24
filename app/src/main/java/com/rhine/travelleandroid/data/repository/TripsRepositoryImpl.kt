package com.rhine.travelleandroid.data.repository

import com.rhine.travelleandroid.data.local.dao.*
import com.rhine.travelleandroid.data.mapper.*
import com.rhine.travelleandroid.data.remote.api.TripsAPI
import com.rhine.travelleandroid.domain.model.*
import com.rhine.travelleandroid.domain.repository.TripsRepository
import com.rhine.travelleandroid.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TripsRepositoryImpl @Inject constructor(
    private val api: TripsAPI,
    private val tripDao: TripDao,
    private val tripDayDao: TripDayDao,
    private val dayActivityDao: DayActivityDao
) : TripsRepository {

    override suspend fun getTrips(): Result<List<Trip>> = safeApiCall {
        val response = api.getTrips()
        val tripDtos = response.trips ?: emptyList()

        val tripEntities = tripDtos.map { it.toEntity() }
        val tripDayEntities = mutableListOf<com.rhine.travelleandroid.data.local.entity.TripDayEntity>()
        val dayActivityEntities = mutableListOf<com.rhine.travelleandroid.data.local.entity.DayActivityEntity>()

        tripDtos.forEach { tripDto ->
            val tripEntity = tripDto.toEntity()
            val tripId = tripEntity.id

            tripDto.tripDays.forEach { dayDto ->
                val dayEntity = dayDto.toEntity(tripId)
                tripDayEntities.add(dayEntity)

                dayDto.dayActivities.forEach { actDto ->
                    val actEntity = actDto.toEntity(dayEntity.id)
                    dayActivityEntities.add(actEntity)
                }
            }
        }

        withContext(Dispatchers.IO) {
            tripDao.clearTrips()
            tripDao.insertTrips(tripEntities)
            tripDayDao.insertTripDays(tripDayEntities)
            dayActivityDao.insertDayActivities(dayActivityEntities)
        }

        tripDao.getAllTripsWithRelations().map { it.toDomain() }
    }

    override suspend fun getTripId(id: Int): Result<Trip> = safeApiCall {
        val response = api.getTripId(id)
        val dto = response.trip ?: throw Exception("Trip not found")

        withContext(Dispatchers.IO) {
            tripDao.insertTrip(dto.toEntity())
            tripDayDao.deleteTripDaysByTripId(dto.id)
            dayActivityDao.clearActivities()
        }

        dto.toDomain()
    }

    override suspend fun syncTrips(trips: List<Trip>): Result<Boolean> = safeApiCall {
        api.tripSync(emptyList())
        true
    }

    override suspend fun deleteTrip(id: Int): Result<Boolean> = safeApiCall {
        api.tripDelete(id)
        withContext(Dispatchers.IO) {
            tripDao.deleteTripById(id)
            tripDayDao.deleteTripDaysByTripId(id)
        }
        true
    }

    override suspend fun regenerateTrip(id: Int): Result<Trip> = safeApiCall {
        val response = api.tripRegenerate(id)
        val dto = response.trip ?: throw Exception("Empty trip")
        withContext(Dispatchers.IO) { tripDao.insertTrip(dto.toEntity()) }
        dto.toDomain()
    }

    override suspend fun editTrip(tripId: Int, request: Request?): Result<Trip> = safeApiCall {
        val response = api.tripEdit(tripId, request?.toDto())
        val dto = response.trip ?: throw Exception("Empty trip")
        withContext(Dispatchers.IO) { tripDao.insertTrip(dto.toEntity()) }
        dto.toDomain()
    }

    override suspend fun voteForTrip(tripId: Int, aiResultsVote: String): Result<Boolean> = safeApiCall {
        api.tripVote(tripId, aiResultsVote)
        true
    }

    override suspend fun createTrip(request: Request?): Result<Trip> = safeApiCall {
        val response = api.tripCreate(request?.toDto())
        val dto = response.trip ?: throw Exception("Empty trip")
        withContext(Dispatchers.IO) { tripDao.insertTrip(dto.toEntity()) }
        dto.toDomain()
    }

    override suspend fun tripSurprise(request: Request?): Result<List<Trip>> = safeApiCall {
        val response = api.tripSurprise(request?.toDto())
        val dtos = response.trips ?: emptyList()
        dtos.map { it.toDomain() }
    }

    override suspend fun saveSurpriseTrip(trip: Trip?): Result<Trip> = safeApiCall {
        val req = trip?.toTripSaveRequest()
        val response = api.tripSurpriseSave(req)
        val dto = response.trip ?: throw Exception("Empty trip")
        dto.toDomain()
    }

    override suspend fun editTripDay(
        tripDayId: Int,
        destination: String,
        budget: Double,
        context: String
    ): Result<TripDay> = safeApiCall {
        val response = api.tripDayEdit(tripDayId, destination, budget, context)
        val dto = response.tripDay ?: throw Exception("Empty trip day")
        dto.toDomain()
    }

    override suspend fun regenerateTripDay(tripDayId: Int): Result<TripDay> = safeApiCall {
        val response = api.tripDayRegenerate(tripDayId)
        val dto = response.tripDay ?: throw Exception("Empty trip day")
        dto.toDomain()
    }

    override suspend fun editTripDayActivity(dayActivityId: Int, context: String): Result<DayActivity> =
        safeApiCall {
            val response = api.tripDayActivityEdit(dayActivityId, context)
            val dto = response.dayActivity ?: throw Exception("Empty day activity")
            dto.toDomain()
        }

    override fun cancelTripCreation() {
        // Retrofit coroutines can't be canceled directly
    }
}