package com.rhine.travelleandroid.di.providers

import com.rhine.travelleandroid.data.remote.api.AuthAPI
import com.rhine.travelleandroid.di.modules.GenericApiProvider
import javax.inject.Provider

class AuthApiProvider(
    private val genericApiProvider: GenericApiProvider
) : Provider<AuthAPI> {
    override fun get(): AuthAPI {
        return genericApiProvider.create(AuthAPI::class.java)
    }
}
