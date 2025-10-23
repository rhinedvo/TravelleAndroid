package com.rhine.travelleandroid.data.local.repository

import com.rhine.travelleandroid.data.local.database.TokenDao
import com.rhine.travelleandroid.data.local.database.model.TokenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val tokenDao: TokenDao
) {
    suspend fun getToken(): TokenEntity? {
        return tokenDao.getToken()
    }
    
    suspend fun getTokenString(): String? {
        val token = tokenDao.getToken()
        return token?.let { "${it.tokenType} ${it.accessToken}" }
    }
    
    suspend fun insertToken(token: TokenEntity) {
        tokenDao.insertToken(token)
    }
    
    suspend fun deleteToken() {
        tokenDao.deleteToken()
    }
}
