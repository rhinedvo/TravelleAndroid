package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.Requests
import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripEditUseCase(
    private val repository: TripsRepository,
) {
    suspend fun execute(tripId: Int, requests: Requests): Result<TripDTO> {
        return repository.tripEdit(tripId, requests)
    }
    fun cancel() {
        repository.cancelTripCreate()
    }
}