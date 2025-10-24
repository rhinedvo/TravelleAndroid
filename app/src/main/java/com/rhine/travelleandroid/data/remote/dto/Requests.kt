package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Requests(
    @Json(name = "destination")
    var destination: String? = "",
    @Json(name = "budget")
    var budget: Double? = 0.0,
    @Json(name = "context")
    var context: String? = "",
    @Json(name = "timeframe")
    var timeframe: String? = "",
    @Json(name = "origin")
    var origin: String? = "",
    @Json(name = "include_estimated_costs")
    var includeEstimatedCosts: Boolean? = false
)