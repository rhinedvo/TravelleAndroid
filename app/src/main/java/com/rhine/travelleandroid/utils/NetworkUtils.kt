package com.rhine.travelleandroid.utils


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> safeApiCall(crossinline call: suspend () -> T): Result<T> {
    return try {
        withContext(Dispatchers.IO) {
            Result.success(call())
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}