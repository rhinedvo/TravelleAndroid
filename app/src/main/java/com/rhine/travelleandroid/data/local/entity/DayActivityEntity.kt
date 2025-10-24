package com.rhine.travelleandroid.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "day_activities",
    foreignKeys = [
        ForeignKey(
            entity = TripDayEntity::class,
            parentColumns = ["id"],
            childColumns = ["tripDayId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DayActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tripDayId: Int,
    val dayActivitySegment: String,
    val name: String,
    val details: String?,
    val cost: Double?,
    val externalUrl: String?
)