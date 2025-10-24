package com.rhine.travelleandroid.data.repository

import com.rhine.travelleandroid.data.ResponseHandler
import com.rhine.travelleandroid.data.remote.api.ListAPI
import com.rhine.travelleandroid.data.remote.dto.BaseResponse
import com.rhine.travelleandroid.domain.repository.ListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ListRepositoryImpl @Inject constructor(
    private val listAPI: ListAPI
) : ListRepository {

    override suspend fun getList(): Result<Boolean> = suspendCancellableCoroutine { continuation ->
        val handler = object : ResponseHandler<BaseResponse>() {
            override fun onSuccess(response: BaseResponse) {
                if (continuation.isActive) continuation.resume(Result.success(true))
            }

            override fun onError(error: Throwable) {
                if (continuation.isActive) continuation.resume(Result.failure(error))
            }

            override fun noInternet() {
                if (continuation.isActive)
                    continuation.resume(Result.failure(Exception("No internet connection")))
            }
        }

        listAPI.getList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(handler)

        continuation.invokeOnCancellation { handler.cancel() }
    }
}