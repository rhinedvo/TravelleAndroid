package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.TripDay
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripRegenerateUseCase@Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(tripDayId: Int): Result<TripDay> {
        return repository.regenerateTripDay(tripDayId)
    }
}