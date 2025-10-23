package com.rhine.travelleandroid.data.repository

import com.kodetechnologies.guzoandroid.common.global.sha256
import com.kodetechnologies.guzoandroid.date.ResponseHandler
import com.kodetechnologies.guzoandroid.date.api.UserAPI
import com.kodetechnologies.guzoandroid.date.database.RealmManager
import com.kodetechnologies.guzoandroid.date.model.Base
import com.kodetechnologies.guzoandroid.date.model.UserDTO
import com.rhine.travelleandroid.data.ResponseHandler
import com.rhine.travelleandroid.data.local.database.model.Base
import com.rhine.travelleandroid.data.local.database.model.UserDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import toothpick.InjectConstructor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface UserRepository {
    suspend fun getProfile(): Result<Boolean>
    suspend fun editProfile(profile: UserDTO): Result<Boolean>
    suspend fun deleteProfile(): Result<Boolean>
    suspend fun changePassword(newPassword: String, currentPassword: String): Result<Boolean>
    suspend fun changeEmail(newEmail: String): Result<Boolean>
}

@InjectConstructor
class UserRepositoryImpl(
    private val userApi: UserAPI,
) : UserRepository {
    override suspend fun getProfile(): Result<Boolean> = suspendCoroutine { continuation ->
        userApi.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : ResponseHandler<Base>() {
                override fun onSuccess(response: Base) {
                    response.user?.toEntity()?.let {
                        RealmManager.save(it)
                    }
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

    override suspend fun editProfile(profile: UserDTO): Result<Boolean> =
        suspendCoroutine { continuation ->
            userApi.editUser(profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : ResponseHandler<Base>() {
                    override fun onSuccess(response: Base) {
                        response.user?.toEntity()?.let {
                            RealmManager.save(it)
                        }
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

    override suspend fun deleteProfile(): Result<Boolean> = suspendCoroutine { continuation ->
        userApi.deleteUser()
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

    override suspend fun changePassword(
        newPassword: String,
        currentPassword: String,
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            userApi.changePassword(newPassword.sha256(), currentPassword.sha256())
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

    override suspend fun changeEmail(newEmail: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            userApi.changeEmail(newEmail)
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
}