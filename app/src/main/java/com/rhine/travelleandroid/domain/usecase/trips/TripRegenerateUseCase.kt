package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripRegenerateUseCase (
    private val repository: TripsRepository
) {
    suspend fun execute(id: Int): Result<TripDTO> {
        return repository.tripRegenerate(id)
    }
    fun cancel() {
        repository.cancelTripCreate()
    }
}