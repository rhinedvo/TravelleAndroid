package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripSyncUseCase(
    private val repository: TripsRepository
) {
    suspend fun execute(trips: List<TripDTO>): Result<Boolean> {
        return repository.syncTrips(trips)
    }
}