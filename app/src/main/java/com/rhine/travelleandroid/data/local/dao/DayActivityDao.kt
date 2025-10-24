package com.rhine.travelleandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhine.travelleandroid.data.local.entity.DayActivityEntity

@Dao
interface DayActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayActivity(activity: DayActivityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayActivities(activities: List<DayActivityEntity>)

    @Query("SELECT * FROM day_activities WHERE tripDayId = :tripDayId")
    suspend fun getActivitiesByTripDayId(tripDayId: Int): List<DayActivityEntity>

    @Query("DELETE FROM day_activities")
    suspend fun clearActivities()

    @Query("DELETE FROM day_activities WHERE tripDayId = :tripDayId")
    suspend fun deleteActivitiesByTripDayId(tripDayId: Int)
}