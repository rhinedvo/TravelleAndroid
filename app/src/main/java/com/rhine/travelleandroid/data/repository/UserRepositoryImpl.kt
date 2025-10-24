package com.rhine.travelleandroid.data.repository

import com.rhine.travelleandroid.data.local.dao.UserDao
import com.rhine.travelleandroid.data.mapper.toEntity
import com.rhine.travelleandroid.data.remote.api.UserAPI
import com.rhine.travelleandroid.domain.repository.UserRepository
import com.rhine.travelleandroid.utils.safeApiCall
import com.rhine.travelleandroid.utils.sha256
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserAPI,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getProfile(): Result<Boolean> = safeApiCall {
        val response = userApi.getUser()
        val user = response.user ?: throw Exception("User not found")

        withContext(Dispatchers.IO) {
            userDao.clearUser()
            userDao.insertUser(user.toEntity())
        }

        true
    }

    override suspend fun editProfile(profile: com.rhine.travelleandroid.data.remote.dto.UserDTO): Result<Boolean> = safeApiCall {
        val response = userApi.editUser(profile)
        val user = response.user ?: throw Exception("User not found")

        withContext(Dispatchers.IO) {
            userDao.insertUser(user.toEntity())
        }

        true
    }

    override suspend fun deleteProfile(): Result<Boolean> = safeApiCall {
        userApi.deleteUser()
        withContext(Dispatchers.IO) {
            userDao.clearUser()
        }
        true
    }

    override suspend fun changePassword(
        newPassword: String,
        currentPassword: String
    ): Result<Boolean> = safeApiCall {
        userApi.changePassword(newPassword.sha256(), currentPassword.sha256())
        true
    }

    override suspend fun changeEmail(newEmail: String): Result<Boolean> = safeApiCall {
        userApi.changeEmail(newEmail)
        true
    }
}