package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripDTO(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "trip_name") val title: String = "",
    @Json(name = "destination") val destination: String = "",
    @Json(name = "description") val descriptionText: String = "",
    @Json(name = "timeframe") val timeframe: String = "",
    @Json(name = "trip_status") val tripStatus: String = "",
    @Json(name = "total_budget") val totalBudget: Double = 0.0,
    @Json(name = "ai_results_vote") val aiResultsVote: String = "",
    @Json(name = "origin") val origin: String = "",
    @Json(name = "est_flight_cost") val estFlightCost: Double = 0.0,
    @Json(name = "est_accommodation_cost") val estAccommodationCost: Double = 0.0,
    @Json(name = "include_estimated_costs") val includeEstimatedCosts: Boolean = false,
    @Json(name = "created_at") val createdAt: String = "",
    @Json(name = "updated_at") val updatedAt: String = "",
    @Json(name = "context") val context: String = "",
    @Json(name = "days") val tripDays: List<TripDayDTO> = emptyList()
)