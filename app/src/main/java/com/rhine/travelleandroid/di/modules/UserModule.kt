package com.rhine.travelleandroid.di.modules

import com.rhine.travelleandroid.data.remote.api.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserAPI(retrofit: Retrofit): UserAPI =
        retrofit.create(UserAPI::class.java)

}
