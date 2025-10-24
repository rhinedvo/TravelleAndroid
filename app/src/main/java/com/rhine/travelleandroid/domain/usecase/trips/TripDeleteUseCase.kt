package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripDeleteUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return repository.deleteTrip(id)
    }
}