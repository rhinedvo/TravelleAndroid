package com.rhine.travelleandroid.data


import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class ResponseHandler<T> : SingleObserver<T> {
    private val gson = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    protected var disposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        disposable = d
        Log.d("ResponseHandler", "Request started")
        if (isCancelled()) {
            d.dispose()
            return
        }
    }

    fun cancel() {
        disposable?.dispose()
        disposable = null
    }

    protected fun isCancelled(): Boolean {
        return disposable?.isDisposed == true
    }

    override fun onError(e: Throwable) {
        Log.e("ResponseHandler", "Error: ${e.message}", e)
        when (e) {
            is HttpException -> {
                try {
                    val body = e.response()?.errorBody()
                    val code = e.response()?.code()
                    Log.e("ResponseHandler", "HTTP Error: $code, Body: ${body?.string()}")
//                    if (401 == code) {
//                        EventBus.getDefault().post(LogoutEvent())
//                        return
//                    }
//                    val adapter = gson.adapter(Base::class.java)
//                    val errorParser = adapter.fromJson(body?.string())
//                    errorParser?.code = code
//                    errorParser?.let {
//                        apiException(it)
//                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            is SocketTimeoutException -> {
                try {
                    noInternet()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            is UnknownHostException -> {
                try {
                    noInternet()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            else -> {
                e.printStackTrace()
            }
        }
    }

    open fun logout() {}

    open fun noInternet() {}

    open fun retry() {}

//    open fun apiException() {}
}