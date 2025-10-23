package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.Requests
import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripCreateUseCase(
    private val repository: TripsRepository
) {
    suspend fun execute(request: Requests?): Result<TripDTO> {
        return repository.tripCreate(request)
    }

    fun cancel() {
        repository.cancelTripCreate()
    }
}