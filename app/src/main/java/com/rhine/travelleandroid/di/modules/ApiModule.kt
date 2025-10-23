package com.rhine.travelleandroid.di.modules

import com.rhine.travelleandroid.di.ServerPath
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideGenericApiProvider(
        @ServerPath retrofit: Retrofit
    ): GenericApiProvider {
        return GenericApiProvider(retrofit)
    }
}

class GenericApiProvider(private val retrofit: Retrofit) {
    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
