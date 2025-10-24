package com.rhine.travelleandroid.data.local.repository

import com.rhine.travelleandroid.data.local.dao.TokenDao
import com.rhine.travelleandroid.data.local.entity.TokenEntity
import com.rhine.travelleandroid.data.mapper.toEntity
import com.rhine.travelleandroid.data.remote.dto.TokenDTO
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenDao: TokenDao
) {
    suspend fun saveToken(dto: TokenDTO) {
        tokenDao.insertToken(dto.toEntity())
    }

    suspend fun getToken(): TokenEntity? = tokenDao.getToken()

    suspend fun clearToken() = tokenDao.clearToken()
}