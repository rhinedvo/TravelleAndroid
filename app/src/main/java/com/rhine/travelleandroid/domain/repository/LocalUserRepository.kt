package com.rhine.travelleandroid.domain.repository

import com.rhine.travelleandroid.data.local.dao.UserDao
import com.rhine.travelleandroid.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUserFlow(): Flow<UserEntity?> = userDao.getUserFlow()

    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)

    suspend fun clearUser() = userDao.clearUser()
}