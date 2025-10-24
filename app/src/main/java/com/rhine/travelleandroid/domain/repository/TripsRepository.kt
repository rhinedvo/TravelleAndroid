package com.rhine.travelleandroid.domain.repository

import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.model.TripDay
import com.rhine.travelleandroid.domain.model.DayActivity
import com.rhine.travelleandroid.domain.model.Request

interface TripsRepository {

    suspend fun getTrips(): Result<List<Trip>>

    suspend fun getTripId(id: Int): Result<Trip>

    suspend fun syncTrips(trips: List<Trip>): Result<Boolean>

    suspend fun deleteTrip(id: Int): Result<Boolean>

    suspend fun regenerateTrip(id: Int): Result<Trip>

    suspend fun editTrip(tripId: Int, request: Request?): Result<Trip>

    suspend fun voteForTrip(tripId: Int, aiResultsVote: String): Result<Boolean>

    suspend fun createTrip(request: Request?): Result<Trip>

    suspend fun tripSurprise(request: Request?): Result<List<Trip>>

    suspend fun saveSurpriseTrip(trip: Trip?): Result<Trip>

    suspend fun editTripDay(
        tripDayId: Int,
        destination: String,
        budget: Double,
        context: String
    ): Result<TripDay>

    suspend fun regenerateTripDay(tripDayId: Int): Result<TripDay>

    suspend fun editTripDayActivity(dayActivityId: Int, context: String): Result<DayActivity>

    fun cancelTripCreation()
}