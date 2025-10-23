package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class TripSurpriseSaveUseCase(
    private val tripsRepository: TripsRepository
) {
    suspend fun execute(trip: TripDTO?): Result<TripDTO> {
        return tripsRepository.tripSurpriseSave(trip)
    }
}
