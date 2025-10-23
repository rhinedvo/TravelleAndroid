package com.rhine.travelleandroid.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rhine.travelleandroid.data.local.database.model.TokenEntity
import com.rhine.travelleandroid.data.local.database.model.UserEntity

@Database(
    entities = [TokenEntity::class, UserEntity::class], 
    version = 2, 
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun userDao(): UserDao
}
