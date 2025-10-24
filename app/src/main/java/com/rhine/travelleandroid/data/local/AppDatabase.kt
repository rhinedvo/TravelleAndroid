package com.rhine.travelleandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rhine.travelleandroid.data.local.dao.*
import com.rhine.travelleandroid.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        TokenEntity::class,
        TripEntity::class,
        TripDayEntity::class,
        DayActivityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun tokenDao(): TokenDao
    abstract fun tripDao(): TripDao
    abstract fun tripDayDao(): TripDayDao
    abstract fun dayActivityDao(): DayActivityDao
}