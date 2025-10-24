package com.rhine.travelleandroid.data.mapper

import com.rhine.travelleandroid.data.local.entity.UserEntity
import com.rhine.travelleandroid.data.remote.dto.UserDTO
import com.rhine.travelleandroid.domain.model.User

fun UserDTO.toEntity() = UserEntity(
    id = id,
    tokenType = tokenType,
    accessToken = accessToken,
    fullName = fullName,
    email = email,
    notificationsEnabled = notificationsEnabled,
    isEmailVerified = isEmailVerified,
    isGuest = isGuest
)

fun UserEntity.toDomain() = User(
    id = id,
    tokenType = tokenType ?: "",
    accessToken = accessToken ?: "",
    fullName = fullName,
    email = email,
    notificationsEnabled = notificationsEnabled,
    isEmailVerified = isEmailVerified,
    isGuest = isGuest
)

fun UserDTO.toDomain() = User(
    id = id,
    tokenType = tokenType,
    accessToken = accessToken,
    fullName = fullName,
    email = email,
    notificationsEnabled = notificationsEnabled,
    isEmailVerified = isEmailVerified,
    isGuest = isGuest
)