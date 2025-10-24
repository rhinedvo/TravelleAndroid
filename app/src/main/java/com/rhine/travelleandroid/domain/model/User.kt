package com.rhine.travelleandroid.domain.model

data class User(
    val id: Int,
    val tokenType: String,
    val accessToken: String,
    val fullName: String,
    val email: String,
    val notificationsEnabled: Boolean,
    val isEmailVerified: Boolean,
    val isGuest: Boolean
)