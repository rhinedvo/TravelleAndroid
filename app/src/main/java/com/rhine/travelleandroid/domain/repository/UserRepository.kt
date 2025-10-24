package com.rhine.travelleandroid.domain.repository


import com.rhine.travelleandroid.data.remote.dto.UserDTO

interface UserRepository {
    suspend fun getProfile(): Result<Boolean>
    suspend fun editProfile(profile: UserDTO): Result<Boolean>
    suspend fun deleteProfile(): Result<Boolean>
    suspend fun changePassword(newPassword: String, currentPassword: String): Result<Boolean>
    suspend fun changeEmail(newEmail: String): Result<Boolean>
}