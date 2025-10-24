package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseResponse {
    @Json(name = "code")
    var code: Int? = null

    @Json(name = "message")
    var message: String? = ""

    @Json(name = "title")
    var title: String? = ""

    @Json(name = "trips")
    var trips: List<TripDTO> = ArrayList()

    @Json(name = "trip")
    var trip: TripDTO? = null

    @Json(name = "user")
    var user: UserDTO? = null

    @Json(name = "isset_email")
    var isSetEmail: Boolean = false

    @Json(name = "day_activity")
    var dayActivity: DayActivityDTO? = null

    @Json(name = "trip_day")
    var tripDay: TripDayDTO? = null

    @Json(name = "suggestions")
    var suggestions: List<TripDTO> = ArrayList()
}