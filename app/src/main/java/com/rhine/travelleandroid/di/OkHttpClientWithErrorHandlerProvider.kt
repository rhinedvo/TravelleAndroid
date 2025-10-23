package com.rhine.travelleandroid.di

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Provider
class OkHttpClientWithErrorHandlerProvider : Provider<OkHttpClient> {
    override fun get(): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(30, TimeUnit.MINUTES)
        readTimeout(30, TimeUnit.MINUTES)
        writeTimeout(60, TimeUnit.MINUTES)
        addNetworkInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        addInterceptor(AuthInterceptor())
        build()
    }

    inner class AuthInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val requestBuilder = original.newBuilder()
//            val token = userToken
//            if (!token.isNullOrEmpty()) {
//                requestBuilder.addHeader("Authorization", token)
//            }
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.method(original.method, original.body)
            return chain.proceed(requestBuilder.build())
        }
    }
}