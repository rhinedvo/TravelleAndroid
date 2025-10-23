package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.Requests
import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripSurpriseUseCase(
    private val repository: TripsRepository,
) {
    suspend fun execute(requests: Requests): Result<List<TripDTO>> {
        return repository.tripSurprise(requests)
    }
}