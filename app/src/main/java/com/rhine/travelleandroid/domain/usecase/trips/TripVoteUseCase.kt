package com.rhine.travelleandroid.domain.usecase.trips

import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripVoteUseCase (
    private val repository: TripsRepository
) {
    suspend fun execute(tripId: Int, vote: String): Result<Boolean> {
        return repository.tripVote(tripId, vote)
    }
}