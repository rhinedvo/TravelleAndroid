package com.rhine.travelleandroid.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TripDayWithActivities(
    @Embedded val day: TripDayEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripDayId"
    )
    val activities: List<DayActivityEntity>
)