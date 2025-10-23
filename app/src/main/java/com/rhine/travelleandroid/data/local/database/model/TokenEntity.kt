package com.rhine.travelleandroid.data.local.database.model

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

// Embedded user class without primary key to avoid Room warnings
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

@JsonClass(generateAdapter = true)
data class TokenDTO(
    @Json(name = "id")
    val id: Int = 1,

    @Json(name = "token_type")
    val tokenType: String = "",

    @Json(name = "token")
    val accessToken: String = "",

    @Json(name = "user")
    val user: UserDTO? = null
) {
    fun toEntity(): TokenEntity {
        return TokenEntity(
            id = this.id,
            tokenType = this.tokenType,
            accessToken = this.accessToken,
            user = this.user?.let { userDto ->
                EmbeddedUser(
                    id = userDto.id,
                    tokenType = userDto.tokenType,
                    accessToken = userDto.accessToken,
                    fullName = userDto.fullName,
                    email = userDto.email,
                    notificationsEnabled = userDto.notificationsEnabled,
                    isEmailVerified = userDto.isEmailVerified,
                    isGuest = userDto.isGuest
                )
            }
        )
    }
}
