package com.rhine.travelleandroid.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TripWithDaysAndActivities(
    @Embedded val trip: TripEntity,
    @Relation(
        entity = TripDayEntity::class,
        parentColumn = "id",
        entityColumn = "tripId"
    )
    val days: List<TripDayWithActivities>
)