package com.rhine.travelleandroid.data.remote.dto

data class TripSurpriseResponse(
    val trips: List<TripDTO>? = null,
    val message: String? = null,
    val status: String? = null
)