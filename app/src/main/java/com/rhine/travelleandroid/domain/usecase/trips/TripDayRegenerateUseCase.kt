package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripDayRegenerateUseCase(
    private val repository: TripsRepository
) {
    suspend fun execute(tripDayId: Int): Result<Boolean> {
        return repository.tripDayRegenerate(tripDayId)
    }
}