package com.rhine.travelleandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val destination: String = "",
    val descriptionText: String = "",
    val timeframe: String = "",
    val tripStatus: String = "",
    val totalBudget: Double = 0.0,
    val aiResultsVote: String = "",
    val origin: String = "",
    val estFlightCost: Double = 0.0,
    val estAccommodationCost: Double = 0.0,
    val includeEstimatedCosts: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = "",
    val context: String = ""
)