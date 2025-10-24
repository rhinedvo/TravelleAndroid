package com.rhine.travelleandroid.data.remote.api

import com.rhine.travelleandroid.data.remote.dto.BaseResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ListAPI  {
    @GET("/api/system-list")
    fun getList(): Single<BaseResponse>
}