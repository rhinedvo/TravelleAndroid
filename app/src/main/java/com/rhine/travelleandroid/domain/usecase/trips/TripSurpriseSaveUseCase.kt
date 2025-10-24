package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripSurpriseSaveUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(trip: Trip?): Result<Trip> {
        return repository.saveSurpriseTrip(trip)
    }
}