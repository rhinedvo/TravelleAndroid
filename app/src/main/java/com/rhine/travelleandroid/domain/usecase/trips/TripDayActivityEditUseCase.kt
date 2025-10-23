package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripDayActivityEditUseCase (
    private val repository: TripsRepository
) {
    suspend fun execute(dayActivityId: Int, context: String): Result<Boolean> {
        return repository.tripDayActivityEdit(dayActivityId,context)
    }
}