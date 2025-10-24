package com.rhine.travelleandroid.data.repository


import com.rhine.travelleandroid.data.local.dao.TokenDao
import com.rhine.travelleandroid.data.local.dao.UserDao
import com.rhine.travelleandroid.data.remote.api.AuthAPI
import com.rhine.travelleandroid.data.local.entity.TokenEntity
import com.rhine.travelleandroid.data.local.entity.UserEntity
import com.rhine.travelleandroid.data.mapper.toEntity
import com.rhine.travelleandroid.domain.repository.AuthRepository
import com.rhine.travelleandroid.utils.sha256
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthAPI,
    private val userDao: UserDao,
    private val tokenDao: TokenDao
) : AuthRepository {

    override suspend fun requestToken(): Result<Boolean> = safeApiCall {
        api.requestToken()
        true
    }

    override suspend fun login(email: String, password: String): Result<Boolean> = safeApiCall {
        val dto = api.login(email, password.sha256()).blockingGet()
        saveTokenAndUser(dto.toEntity())
        true
    }

    override suspend fun register(name: String, email: String, password: String): Result<Boolean> = safeApiCall {
        val dto = api.register(name, email, password.sha256()).blockingGet()
        saveTokenAndUser(dto.toEntity())
        true
    }

    override suspend fun forgotPassword(email: String): Result<Boolean> = safeApiCall {
        api.forgotPassword(email)
        true
    }

    override suspend fun guestRegister(): Result<Boolean> = safeApiCall {
        val dto = api.guestRegister().blockingGet()
        saveTokenAndUser(dto.toEntity())
        true
    }

    override suspend fun checkEmail(email: String): Result<Boolean> = safeApiCall {
        val response = api.checkEmail(email).blockingGet()
        if (response.isSetEmail) true else throw Exception("No account found with this email.")
    }

    private suspend fun saveTokenAndUser(dto: TokenEntity) {
        withContext(Dispatchers.IO) {
            tokenDao.clearToken()
            userDao.clearUser()
            tokenDao.insert(dto)
            userDao.insert(UserEntity.fromToken(dto))
        }
    }

    private suspend inline fun <T> safeApiCall(crossinline call: suspend () -> T): Result<T> {
        return try {
            withContext(Dispatchers.IO) {
                Result.success(call())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}