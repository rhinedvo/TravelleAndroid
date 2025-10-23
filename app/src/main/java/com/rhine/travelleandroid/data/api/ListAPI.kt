package com.rhine.travelleandroid.data.api

import com.rhine.travelleandroid.data.local.database.model.Base
import io.reactivex.Single
import retrofit2.http.GET

interface ListAPI  {
    @GET("/api/system-list")
    fun getList(): Single<Base>
}


