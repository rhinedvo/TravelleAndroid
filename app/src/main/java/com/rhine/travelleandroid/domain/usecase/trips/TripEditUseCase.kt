package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.Request
import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripEditUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(tripId: Int, request: Request?): Result<Trip> {
        return repository.editTrip(tripId, request)
    }

    fun cancel() {
        repository.cancelTripCreation()
    }
}