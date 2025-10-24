package com.rhine.travelleandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    val fullName: String,
    val email: String,
    val tokenType: String?,
    val accessToken: String?,
    val notificationsEnabled: Boolean,
    val isEmailVerified: Boolean,
    val isGuest: Boolean
) {
    companion object {
        fun fromToken(token: TokenEntity): UserEntity {
            val user = token.user
            return UserEntity(
                id = user?.id ?: 0,
                fullName = user?.fullName ?: "",
                email = user?.email ?: "",
                tokenType = token.tokenType,
                accessToken = token.accessToken,
                notificationsEnabled = user?.notificationsEnabled ?: false,
                isEmailVerified = user?.isEmailVerified ?: false,
                isGuest = user?.isGuest ?: false
            )
        }
    }
}