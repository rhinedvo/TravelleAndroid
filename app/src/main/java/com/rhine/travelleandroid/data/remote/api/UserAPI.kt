package com.rhine.travelleandroid.data.remote.api

import com.rhine.travelleandroid.data.remote.dto.BaseResponse
import com.rhine.travelleandroid.data.remote.dto.UserDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {

    @GET("/api/user")
    suspend fun getUser(): BaseResponse

    @POST("/api/user/edit")
    suspend fun editUser(
        @Body user: UserDTO
    ): BaseResponse

    @DELETE("/api/user/delete")
    suspend fun deleteUser(): BaseResponse

    @POST("/api/user/change-password")
    @FormUrlEncoded
    suspend fun changePassword(
        @Field("new_password") newPassword: String,
        @Field("current_password") currentPassword: String
    ): BaseResponse

    @POST("/api/user/change-email")
    @FormUrlEncoded
    suspend fun changeEmail(
        @Field("new_email") newEmail: String
    ): BaseResponse
}