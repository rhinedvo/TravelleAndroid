package com.rhine.travelleandroid.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorParser {
    fun parseApiError(throwable: Throwable?): String {
        return when (throwable) {
            null -> "Unknown error occurred"
            is UnknownHostException -> "No internet connection"
            is SocketTimeoutException -> "Request timeout"
            is HttpException -> {
                when (throwable.code()) {
                    401 -> "Invalid credentials"
                    403 -> "Access denied"
                    404 -> "Resource not found"
                    500 -> "Server error"
                    else -> "Network error: ${throwable.code()}"
                }
            }
            else -> throwable.message ?: "Unknown error occurred"
        }
    }
}