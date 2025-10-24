package com.rhine.travelleandroid.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "token_table")
data class TokenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tokenType: String = "",
    val accessToken: String = "",
    @Embedded(prefix = "user_")
    val user: EmbeddedUser? = null
)

data class EmbeddedUser(
    val id: Int = 0,
    val tokenType: String = "",
    val accessToken: String = "",
    val fullName: String = "",
    val email: String = "",
    val notificationsEnabled: Boolean = false,
    val isEmailVerified: Boolean = false,
    val isGuest: Boolean = false
)