package com.rhine.travelleandroid.data.remote.api

import com.rhine.travelleandroid.data.remote.dto.BaseResponse
import com.rhine.travelleandroid.data.remote.dto.TokenDTO
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPI {
    @POST("/api/auth/request-token")
    fun requestToken(): Single<BaseResponse>

    @POST("/api/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Single<TokenDTO>

    @POST("/api/auth/register")
    @FormUrlEncoded
    fun register(
        @Field("full_name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Single<TokenDTO>

    @POST("/api/auth/guest-register")
    fun guestRegister(): Single<TokenDTO>

    @POST("/api/auth/check-email")
    @FormUrlEncoded
    fun checkEmail(
        @Field("email") email: String,
    ): Single<BaseResponse>

    @POST("/api/auth/forgot-password")
    @FormUrlEncoded
    fun forgotPassword(
        @Field("email") email: String,
    ): Single<BaseResponse>

}