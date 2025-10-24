package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.Request
import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripSurpriseUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(request: Request?): Result<List<Trip>> {
        return repository.tripSurprise(request)
    }
}