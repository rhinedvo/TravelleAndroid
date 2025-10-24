package com.rhine.travelleandroid.data.remote.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripDayDTO(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "trip_id") val tripId: Int = 0,
    @Json(name = "day_title") val title: String = "",
    @Json(name = "day_index") val dayIndex: Int = 0,
    @Json(name = "date") val date: String? = "",
    @Json(name = "total_day_cost") val totalDayCost: Double? = 0.0,
    @Json(name = "is_edited") val isEdited: Boolean = false,
    @Json(name = "activities") val dayActivities: List<DayActivityDTO> = emptyList()
)