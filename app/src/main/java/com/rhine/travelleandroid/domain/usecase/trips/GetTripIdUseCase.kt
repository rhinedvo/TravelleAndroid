package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class GetTripIdUseCase (
    private val repository: TripsRepository
) {
    suspend fun execute(id: Int): Result<Boolean> {
        return repository.getTripId(id)
    }
}