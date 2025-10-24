package com.rhine.travelleandroid.di.modules

import com.rhine.travelleandroid.data.remote.api.AuthAPI
import com.rhine.travelleandroid.di.providers.AuthApiProvider
import com.rhine.travelleandroid.di.ServerPath
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    @ServerPath
    fun provideServerPath(): String = "https://dev.kodetechnologies.app/"

    @Provides
    @Singleton
    fun provideAuthAPI(genericApiProvider: GenericApiProvider): AuthAPI =
        AuthApiProvider(genericApiProvider).get()
}