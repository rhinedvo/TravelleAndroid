package com.rhine.travelleandroid.domain.model

data class Request(
    val destination: String = "",
    val budget: Double = 0.0,
    val context: String = "",
    val timeframe: String = "",
    val origin: String = "",
    val includeEstimatedCosts: Boolean = false
)