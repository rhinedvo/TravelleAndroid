package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.DayActivity
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripDayActivityEditUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(dayActivityId: Int, context: String): Result<DayActivity> {
        return repository.editTripDayActivity(dayActivityId, context)
    }
}