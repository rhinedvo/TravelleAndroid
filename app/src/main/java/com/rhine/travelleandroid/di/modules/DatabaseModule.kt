package com.rhine.travelleandroid.di.modules

import android.content.Context
import androidx.room.Room
import com.rhine.travelleandroid.data.local.AppDatabase
import com.rhine.travelleandroid.data.local.dao.TokenDao
import com.rhine.travelleandroid.data.local.dao.UserDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "guzo_database")
            .build()

    @Provides fun provideUserDao(db: AppDatabase) = db.userDao()
    @Provides fun provideTokenDao(db: AppDatabase) = db.tokenDao()
    @Provides fun provideTripDao(db: AppDatabase) = db.tripDao()
    @Provides fun provideTripDayDao(db: AppDatabase) = db.tripDayDao()
    @Provides fun provideDayActivityDao(db: AppDatabase) = db.dayActivityDao()
}
