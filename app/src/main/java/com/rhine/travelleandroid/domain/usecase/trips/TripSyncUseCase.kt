package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripSyncUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(trips: List<Trip>): Result<Boolean> {
        return repository.syncTrips(trips)
    }
}