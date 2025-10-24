package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenDTO(
    @Json(name = "id") val id: Int = 1,
    @Json(name = "token_type") val tokenType: String = "",
    @Json(name = "token") val accessToken: String = "",
    @Json(name = "user") val user: UserDTO? = null
)
