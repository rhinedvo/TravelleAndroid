package com.rhine.travelleandroid.data.mapper

import com.rhine.travelleandroid.data.local.entity.EmbeddedUser
import com.rhine.travelleandroid.data.local.entity.TokenEntity
import com.rhine.travelleandroid.data.remote.dto.TokenDTO

fun TokenDTO.toEntity(): TokenEntity {
    return TokenEntity(
        id = id,
        tokenType = tokenType,
        accessToken = accessToken,
        user = user?.let { userDto ->
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