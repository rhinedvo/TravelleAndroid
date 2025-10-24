package com.rhine.travelleandroid.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhine.travelleandroid.data.local.entity.TokenEntity

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(token: TokenEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    @Query("SELECT * FROM token_table LIMIT 1")
    suspend fun getToken(): TokenEntity?

    @Query("DELETE FROM token_table")
    suspend fun clearToken()
}