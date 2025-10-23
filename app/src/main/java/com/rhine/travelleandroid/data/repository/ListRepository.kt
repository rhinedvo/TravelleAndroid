package com.rhine.travelleandroid.data.repository

import com.rhine.travelleandroid.data.ResponseHandler
import com.rhine.travelleandroid.data.api.ListAPI
import com.rhine.travelleandroid.data.local.database.model.Base
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface ListRepository {
    suspend fun getList(): Result<Boolean>
}

class ListRepositoryImpl @Inject constructor(
    private val listAPI: ListAPI
) : ListRepository {
    override suspend fun getList(): Result<Boolean> = suspendCoroutine { continuation ->
        listAPI.getList()
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


