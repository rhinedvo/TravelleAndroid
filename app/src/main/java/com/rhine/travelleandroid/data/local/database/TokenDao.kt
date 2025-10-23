package com.rhine.travelleandroid.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhine.travelleandroid.data.local.database.model.TokenEntity

@Dao
interface TokenDao {
    @Query("SELECT * FROM token_table LIMIT 1")
    suspend fun getToken(): TokenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    @Query("DELETE FROM token_table")
    suspend fun deleteToken()
}
