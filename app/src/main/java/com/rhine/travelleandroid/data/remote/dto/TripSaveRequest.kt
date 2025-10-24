package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripSaveRequest(
    @Json(name = "trip_name")
    val tripName: String? = "",
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "days")
    val days: List<TripDaySaveRequest> = emptyList(),
    @Json(name = "destination")
    val destination: String? = "",
    @Json(name = "budget")
    val budget: Double? = 0.0,
    @Json(name = "context")
    val context: String? = "",
    @Json(name = "timeframe")
    val timeframe: String? = "",
    @Json(name = "est_flight_cost")
    val estFlightCost: Double? = 0.0,
    @Json(name = "est_accommodation_cost")
    val estAccommodationCost: Double? = 0.0,
    @Json(name = "origin")
    val origin: String? = "",
    @Json(name = "include_estimated_costs")
    val includeEstimatedCosts: Boolean? = false
)

@JsonClass(generateAdapter = true)
data class TripDaySaveRequest(
    @Json(name = "day")
    val day: Int = 0,
    @Json(name = "date")
    val date: String? = "",
    @Json(name = "day_title")
    val dayTitle: String? = "",
    @Json(name = "morning")
    val morning: DayActivitySaveRequest? = null,
    @Json(name = "afternoon")
    val afternoon: DayActivitySaveRequest? = null,
    @Json(name = "evening")
    val evening: DayActivitySaveRequest? = null,
    @Json(name = "total_day_cost")
    val totalDayCost: Double? = 0.0
)

@JsonClass(generateAdapter = true)
data class DayActivitySaveRequest(
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "details")
    val details: String? = "",
    @Json(name = "cost")
    val cost: Double? = 0.0,
    @Json(name = "link")
    val link: String? = ""
)