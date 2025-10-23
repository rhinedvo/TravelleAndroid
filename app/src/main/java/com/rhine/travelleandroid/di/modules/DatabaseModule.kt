package com.rhine.travelleandroid.di.modules

import android.content.Context
import androidx.room.Room
import com.rhine.travelleandroid.data.local.database.AppDatabase
import com.rhine.travelleandroid.data.local.database.TokenDao
import com.rhine.travelleandroid.data.local.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "luxe_ai_database"
        ).build()
    }

    @Provides
    fun provideTokenDao(database: AppDatabase): TokenDao {
        return database.tokenDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}
