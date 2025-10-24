package com.rhine.travelleandroid.utils

import android.content.res.Resources
import com.rhine.travelleandroid.BuildConfig

const val STATE_SCOPE_NAME = "state_scope_name"
const val API_INTERNET_ERROR = "api_internet_error"
const val API_SERVER_ERROR = "api_server_error"
var privacyPolicyLink = "${BuildConfig.SERVER_URL}privacy-policy"
var termsOfServiceLink = "${BuildConfig.SERVER_URL}terms-of-use"

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
const val temporyPassword = "kjsDkasf23we"