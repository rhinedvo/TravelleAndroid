package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripEditDayUseCase(
    private val repository: TripsRepository
) {
    suspend fun execute(tripId: Int, destination: String,budget: Double,  context: String): Result<Boolean> {
        return repository.tripDayEdit(tripId, destination, budget, context)
    }
}