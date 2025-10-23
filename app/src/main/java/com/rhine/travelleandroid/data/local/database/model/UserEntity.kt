package com.rhine.travelleandroid.data.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val tokenType: String = "",
    val accessToken: String = "",
    val fullName: String = "",
    val email: String = "",
    val notificationsEnabled: Boolean = false,
    val isEmailVerified: Boolean = false,
    val isGuest: Boolean = false
) {
    fun toDTO(): UserDTO {
        return UserDTO(
            id = this.id,
            tokenType = this.tokenType,
            accessToken = this.accessToken,
            fullName = this.fullName,
            email = this.email,
            notificationsEnabled = this.notificationsEnabled,
            isEmailVerified = this.isEmailVerified,
            isGuest = this.isGuest
        )
    }
}

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "id")
    val id: Int = 0,

    @Json(name = "token_type")
    val tokenType: String = "",

    @Json(name = "token")
    val accessToken: String = "",

    @Json(name = "full_name")
    val fullName: String = "",

    @Json(name = "email")
    val email: String = "",

    @Json(name = "is_notifications_enabled")
    val notificationsEnabled: Boolean = false,

    @Json(name = "is_email_verified")
    val isEmailVerified: Boolean = false,

    @Json(name = "is_guest")
    val isGuest: Boolean = false
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = this.id,
            tokenType = this.tokenType,
            accessToken = this.accessToken,
            fullName = this.fullName,
            email = this.email,
            notificationsEnabled = this.notificationsEnabled,
            isEmailVerified = this.isEmailVerified,
            isGuest = this.isGuest
        )
    }
}
