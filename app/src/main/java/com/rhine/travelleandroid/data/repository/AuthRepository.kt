package com.rhine.travelleandroid.data.repository

import com.rhine.travelleandroid.utils.sha256
import com.rhine.travelleandroid.data.ResponseHandler
import com.rhine.travelleandroid.data.api.AuthAPI
import com.rhine.travelleandroid.data.local.database.model.Base
import com.rhine.travelleandroid.data.local.database.UserDao
import com.rhine.travelleandroid.data.local.repository.TokenRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.rhine.travelleandroid.data.local.database.model.TokenDTO
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun register(name: String, email: String, password: String): Result<Boolean>
    suspend fun forgotPassword(email: String): Result<Boolean>
    suspend fun guestRegister(): Result<Boolean>
    suspend fun checkEmail(email: String): Result<Boolean>
    suspend fun getTokenString(): String?
    suspend fun logout(): Result<Boolean>
}

class AuthRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val tokenRepository: TokenRepository,
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            authAPI.login(email, password.sha256())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<TokenDTO>() {
                    override fun onSuccess(response: TokenDTO) {
                        val entity = response.toEntity()
//                        tokenRepository.insertToken(entity)
//                        entity.user?.let { userDao.insertUser(it) }
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun register(name: String, email: String, password: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            authAPI.register(name, email, password.sha256())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<TokenDTO>() {
                    override fun onSuccess(response: TokenDTO) {
                        // Clear existing data
//                        userDao.deleteUser()
//                        tokenRepository.deleteToken()

                        // Save new data
                        val entity = response.toEntity()
//                        tokenRepository.insertToken(entity)
//                        entity.user?.let { userDao.insertUser(it) }
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun forgotPassword(email: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            authAPI.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun guestRegister(): Result<Boolean> =
        suspendCoroutine { continuation ->
            authAPI.guestRegister()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<TokenDTO>() {
                    override fun onSuccess(response: TokenDTO) {
                        // Clear existing data
//                        userDao.deleteUser()
//                        tokenRepository.deleteToken()

                        // Save new data
                        val entity = response.toEntity()
//                        tokenRepository.insertToken(entity)
//                        entity.user?.let { userDao.insertUser(it) }
                        continuation.resume(Result.success(true))
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun checkEmail(email: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            authAPI.checkEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        if (response.isSetEmail == true) {
                            continuation.resume(Result.success(response.isSetEmail))
                        } else {
                            continuation.resume(Result.failure(Exception("We couldn't find any existing accounts with this email address.")))
                        }
                    }

                    override fun onError(error: Throwable) {
                        continuation.resume(Result.failure(error))
                    }

                    override fun noInternet() {
                        continuation.resume(Result.failure(Exception("No internet connection")))
                    }
                })
        }

    override suspend fun getTokenString(): String? {
        return tokenRepository.getTokenString()
    }

    override suspend fun logout(): Result<Boolean> {
        return try {
            userDao.deleteUser()
            tokenRepository.deleteToken()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
