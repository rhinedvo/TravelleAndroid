package com.rhine.travelleandroid.di.modules

import com.rhine.travelleandroid.data.api.AuthAPI
import com.rhine.travelleandroid.data.repository.AuthRepository
import com.rhine.travelleandroid.data.repository.AuthRepositoryImpl
import com.rhine.travelleandroid.data.local.database.UserDao
import com.rhine.travelleandroid.data.local.repository.TokenRepository
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

    @Provides
    @Singleton
    fun provideAuthRepository(
        authAPI: AuthAPI,
        tokenRepository: TokenRepository,
        userDao: UserDao
    ): AuthRepository = AuthRepositoryImpl(authAPI, tokenRepository, userDao)
}
