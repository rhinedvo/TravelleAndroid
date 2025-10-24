package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.TripDay
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripEditDayUseCase@Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(
        tripDayId: Int,
        destination: String,
        budget: Double,
        context: String
    ): Result<TripDay> {
        return repository.editTripDay(tripDayId, destination, budget, context)
    }
}