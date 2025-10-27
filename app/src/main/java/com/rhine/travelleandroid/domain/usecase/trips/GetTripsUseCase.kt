package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class GetTripsUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(): Result<List<Trip>> {
        return repository.getTrips()
    }
}