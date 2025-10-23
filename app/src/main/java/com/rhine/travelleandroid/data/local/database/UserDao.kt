package com.rhine.travelleandroid.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhine.travelleandroid.data.local.database.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteUser()
}
