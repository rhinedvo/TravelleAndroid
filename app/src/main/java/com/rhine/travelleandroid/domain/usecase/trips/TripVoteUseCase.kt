package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.domain.repository.TripsRepository
import javax.inject.Inject

class TripVoteUseCase @Inject constructor(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(tripId: Int, vote: String): Result<Boolean> {
        return repository.voteForTrip(tripId, vote)
    }
}