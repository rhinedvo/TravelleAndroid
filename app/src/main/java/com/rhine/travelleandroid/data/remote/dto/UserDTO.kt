package com.rhine.travelleandroid.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "token_type") val tokenType: String = "",
    @Json(name = "token") val accessToken: String = "",
    @Json(name = "full_name") val fullName: String = "",
    @Json(name = "email") val email: String = "",
    @Json(name = "is_notifications_enabled") val notificationsEnabled: Boolean = false,
    @Json(name = "is_email_verified") val isEmailVerified: Boolean = false,
    @Json(name = "is_guest") val isGuest: Boolean = false
)