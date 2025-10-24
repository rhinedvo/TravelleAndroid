package com.rhine.travelleandroid.di.modules

import com.rhine.travelleandroid.data.remote.api.TripsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TripsModule {

    @Provides
    @Singleton
    fun provideTripsAPI(genericApiProvider: GenericApiProvider): TripsAPI =
        genericApiProvider.create(TripsAPI::class.java)
}