package com.rhine.travelleandroid.data.local.database.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Base {
    @Json(name = "code")
    var code: Int? = null

    @Json(name = "message")
    var message: String? = ""

    @Json(name = "title")
    var title: String? = ""


    @Json(name = "user")
    var user: UserDTO? = null

    @Json(name = "isset_email")
    var isSetEmail: Boolean = false
}