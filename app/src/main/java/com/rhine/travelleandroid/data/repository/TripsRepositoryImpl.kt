package com.rhine.travelleandroid.data.repository

import com.kodetechnologies.guzoandroid.common.constants.TripStatuses
import com.kodetechnologies.guzoandroid.date.ResponseHandler
import com.kodetechnologies.guzoandroid.date.api.TripsAPI
import com.kodetechnologies.guzoandroid.date.database.RealmManager
import com.kodetechnologies.guzoandroid.date.model.Base
import com.kodetechnologies.guzoandroid.date.model.DayActivityDTO
import com.kodetechnologies.guzoandroid.date.model.Requests
import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.kodetechnologies.guzoandroid.date.model.TripDayDTO
import com.kodetechnologies.guzoandroid.date.model.TripEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.InjectConstructor
import kotlin.collections.get
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface TripsRepository {
    suspend fun getTrips(): Result<List<TripDTO>>
    suspend fun getTripId(id: Int): Result<Boolean>
    suspend fun syncTrips(trips: List<TripDTO>): Result<Boolean>
    suspend fun tripDelete(id: Int): Result<Boolean>
    suspend fun tripRegenerate(id: Int): Result<TripDTO>
    suspend fun tripEdit(tripId: Int, request: Requests?): Result<TripDTO>
    suspend fun tripVote(tripId: Int, aiResultsVote: String): Result<Boolean>
    suspend fun tripCreate(request: Requests?): Result<TripDTO>
    suspend fun tripSurprise(requests: Requests?): Result<List<TripDTO>>
    suspend fun tripSurpriseSave(trip: TripDTO?): Result<TripDTO>
    suspend fun tripDayEdit(
        tripDayId: Int,
        destination: String,
        budget: Double,
        context: String,
    ): Result<Boolean>

    suspend fun tripDayRegenerate(tripDayId: Int): Result<Boolean>
    suspend fun tripDayActivityEdit(dayActivityId: Int, context: String): Result<Boolean>

    fun cancelTripCreate()
}

@InjectConstructor
class TripsRepositoryImpl(
    private val tripsAPI: TripsAPI,
) : TripsRepository {

    override suspend fun getTrips(): Result<List<TripDTO>> =
        suspendCoroutine { continuation ->
            tripsAPI.getTrips()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        val responseTripIds = response.trips.map { it.id }.toSet()

                        val tripsToGenerate = RealmManager.realm
                            ?.query(TripEntity::class)
                            ?.find()
                            ?.filter { trip ->
                                trip.tripStatus != TripStatuses.Draft.id && !responseTripIds.contains(
                                    trip.id
                                )
                            }

                        val firstTrip = tripsToGenerate?.firstOrNull()
                        val requests = Requests().apply {
                            destination = firstTrip?.destination
                            timeframe = firstTrip?.timeframe
                            context = firstTrip?.context
                            budget = firstTrip?.totalBudget
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            tripCreate(requests)
                        }
                        RealmManager.realm
                            ?.query(TripEntity::class)
                            ?.find()
                            ?.filter { trip ->
                                trip.tripStatus != TripStatuses.Draft.id && !responseTripIds.contains(
                                    trip.id
                                )
                            }
                            ?.forEach { RealmManager.remove(it) }

                        response.trips.map { it.toEntity() }.let { entities ->
                            RealmManager.save(entities)
                        }
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun getTripId(id: Int): Result<Boolean> =
        suspendCoroutine { continuation ->
            tripsAPI.getTripId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {

                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun syncTrips(trips: List<TripDTO>): Result<Boolean> =
        suspendCoroutine { continuation ->
            tripsAPI.tripSync(trips)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }


    override suspend fun tripDelete(id: Int): Result<Boolean> = suspendCoroutine { continuation ->
        tripsAPI.tripDelete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    continuation.resume(Result.success(true))
                }

                override fun onError(error: Throwable) {
                    continuation.resume(Result.failure(error))
                }

                override fun noInternet() {
                    continuation.resume(Result.failure(Exception("No internet connection")))
                }
            })
    }

    override suspend fun tripRegenerate(id: Int): Result<TripDTO> =
        suspendCoroutine { continuation ->
            val handler = object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    if (!isCancelled()) {
                        response.trip?.toEntity()?.let { entity ->
                            RealmManager.save(entity)
                        }
                        continuation.resume(Result.success(response.trip) as Result<TripDTO>)
                    }
                }

                override fun onError(error: Throwable) {
                    if (!isCancelled()) {
                        continuation.resume(Result.failure(error))
                    }
                }

                override fun noInternet() {
                    if (!isCancelled()) {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                }
            }

            currentTripCreateHandler = handler

            tripsAPI.tripRegenerate(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(handler)
        }

    override suspend fun tripEdit(tripId: Int, request: Requests?): Result<TripDTO> =
        suspendCoroutine { continuation ->
            val handler = object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    if (!isCancelled()) {
                        response.trip?.toEntity()?.let { entity ->
                            RealmManager.save(entity)
                        }
                        continuation.resume(Result.success(response.trip) as Result<TripDTO>)
                    }
                }

                override fun onError(error: Throwable) {
                    if (!isCancelled()) {
                        continuation.resume(Result.failure(error))
                    }
                }

                override fun noInternet() {
                    if (!isCancelled()) {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                }
            }

            currentTripCreateHandler = handler

            tripsAPI.tripEdit(tripId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(handler)
        }

    override suspend fun tripVote(tripId: Int, aiResultsVote: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            tripsAPI.tripVote(tripId, aiResultsVote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    private var currentTripCreateHandler: ResponseHandler<Base>? = null

    override suspend fun tripCreate(request: Requests?): Result<TripDTO> =
        suspendCoroutine { continuation ->
            val handler = object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    if (!isCancelled()) {
                        response.trip?.toEntity()?.let { entity ->
                            RealmManager.save(entity)
                        }
                        continuation.resume(Result.success(response.trip) as Result<TripDTO>)
                    }
                }

                override fun onError(error: Throwable) {
                    if (!isCancelled()) {
                        continuation.resume(Result.failure(error))
                    }
                }

                override fun noInternet() {
                    if (!isCancelled()) {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                }
            }

            currentTripCreateHandler = handler

            tripsAPI.tripCreate(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(handler)
        }

    override suspend fun tripSurprise(requests: Requests?): Result<List<TripDTO>> =
        suspendCoroutine { continuation ->
            tripsAPI.tripSurprise(requests)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Any>() {
                    override fun onSuccess(response: Any) {
                        val trips = mapToTrips(response)
                        continuation.resume(Result.success(trips))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun tripSurpriseSave(trip: TripDTO?): Result<TripDTO> {
        return suspendCoroutine { continuation ->
            val saveRequest = trip?.toSaveRequest()
            tripsAPI.tripSurpriseSave(saveRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        response.trip?.let { savedTrip ->
                            continuation.resume(Result.success(savedTrip))
                        }
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }
    }

    fun mapToTrips(data: Any?): List<TripDTO> {
        if (data !is Map<*, *>) return emptyList()

        val suggestions = data["suggestions"] as? List<*> ?: return emptyList()

        return suggestions.mapNotNull { tripMap: Any? ->
            if (tripMap !is Map<*, *>) return@mapNotNull null

            val tripName = tripMap["trip_name"] as? String ?: ""
            val description = tripMap["description"] as? String ?: ""
            val estFlightCost = (tripMap["est_flight_cost"] as? Double) ?: 0.0
            val estAccommodationCost = (tripMap["est_accommodation_cost"] as? Double) ?: 0.0
            val origin = tripMap["origin"] as? String ?: ""
            val includeEstimatedCosts = (tripMap["include_estimated_costs"] as? Boolean) ?: false

            val days = tripMap["days"] as? List<*> ?: emptyList<Any?>()

            val tripDays = days.mapNotNull { dayMap: Any? ->
                if (dayMap !is Map<*, *>) return@mapNotNull null

                val dayIndex = (dayMap["day"] as? Double)?.toInt() ?: 0
                val date = dayMap["date"] as? String ?: ""
                val dayTitle = dayMap["day_title"] as? String ?: ""
                val totalDayCost = (dayMap["total_day_cost"] as? Double) ?: 0.0

                val morning = dayMap["morning"] as? Map<*, *>
                val afternoon = dayMap["afternoon"] as? Map<*, *>
                val evening = dayMap["evening"] as? Map<*, *>

                val activities = listOfNotNull(
                    morning?.toDayActivityDTO("morning"),
                    afternoon?.toDayActivityDTO("afternoon"),
                    evening?.toDayActivityDTO("evening")
                )

                TripDayDTO(
                    dayIndex = dayIndex,
                    date = date,
                    title = dayTitle,
                    totalDayCost = totalDayCost,
                    dayActivities = activities
                )
            }

            TripDTO(
                id = 0,
                title = tripName,
                descriptionText = description,
                estFlightCost = estFlightCost,
                estAccommodationCost = estAccommodationCost,
                origin = origin,
                includeEstimatedCosts = includeEstimatedCosts,
                tripDays = tripDays
            )
        }
    }

    fun Map<*, *>.toDayActivityDTO(segment: String) = DayActivityDTO(
        dayActivitySegment = segment,
        name = this["name"] as? String ?: "",
        details = this["details"] as? String ?: "",
        cost = (this["cost"] as? Double) ?: 0.0,
        externalUrl = this["link"] as? String ?: ""
    )

    override fun cancelTripCreate() {
        currentTripCreateHandler?.cancel()
        currentTripCreateHandler = null
    }

    override suspend fun tripDayEdit(
        tripDayId: Int,
        destination: String, budget: Double, context: String,
    ): Result<Boolean> = suspendCoroutine { continuation ->
        tripsAPI.tripDayEdit(tripDayId, "\"$destination\"", budget, "\"$context\"")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    response.tripDay?.toEntity()?.let { entity ->
                        RealmManager.save(entity)
                    }
                    continuation.resume(Result.success(true))
                }

                override fun onError(error: Throwable) {
                    continuation.resume(Result.failure(error))
                }

                override fun noInternet() {
                    continuation.resume(Result.failure(Exception("No internet connection")))
                }
            })
    }

    override suspend fun tripDayRegenerate(tripDayId: Int): Result<Boolean> =
        suspendCoroutine { continuation ->
            tripsAPI.tripDayRegenerate(tripDayId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        response.tripDay?.toEntity()?.let { entity ->
                            RealmManager.save(entity)
                        }
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun tripDayActivityEdit(
        dayActivityId: Int,
        context: String,
    ): Result<Boolean> = suspendCoroutine { continuation ->
        tripsAPI.tripDayActivityEdit(dayActivityId, "\"$context\"")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    response.dayActivity?.toEntity()?.let { entity ->
                        RealmManager.save(entity)
                    }
                    continuation.resume(Result.success(true))
                }

                override fun onError(error: Throwable) {
                    continuation.resume(Result.failure(error))
                }

                override fun noInternet() {
                    continuation.resume(Result.failure(Exception("No internet connection")))
                }
            })
    }

}