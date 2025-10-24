package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayActivityDTO(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "trip_day_id") val tripDayId: Int = 0,
    @Json(name = "segment") val dayActivitySegment: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "details") val details: String = "",
    @Json(name = "cost") val cost: Double = 0.0,
    @Json(name = "link") val externalUrl: String = ""
)