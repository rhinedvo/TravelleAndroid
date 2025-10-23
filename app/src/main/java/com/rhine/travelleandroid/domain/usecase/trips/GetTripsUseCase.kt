package com.rhine.travelleandroid.domain.usecase.trips

import com.kodetechnologies.guzoandroid.date.model.TripDTO
import com.rhine.travelleandroid.data.repository.TripsRepository
import toothpick.InjectConstructor

@InjectConstructor
class GetTripsUseCase(
    private val repository: TripsRepository
) {
    suspend operator fun invoke(): List<TripDTO> {
        val result = repository.getTrips()
        return if (result.isSuccess) {
            listOf()
        } else {
            emptyList()
        }
    }
}
