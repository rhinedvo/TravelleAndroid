package com.rhine.travelleandroid.di.modules

import com.rhine.travelleandroid.data.remote.api.ListAPI
import com.rhine.travelleandroid.data.repository.ListRepositoryImpl
import com.rhine.travelleandroid.domain.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListModule {

    @Provides
    @Singleton
    fun provideListAPI(genericApiProvider: GenericApiProvider): ListAPI =
        genericApiProvider.create(ListAPI::class.java)

    @Provides
    @Singleton
    fun provideListRepository(listAPI: ListAPI): ListRepository = ListRepositoryImpl(listAPI)
}


