package com.rhine.travelleandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rhine.travelleandroid.data.local.entity.DayActivityEntity
import com.rhine.travelleandroid.data.local.entity.TripDayEntity
import com.rhine.travelleandroid.data.local.entity.TripDayWithActivities

@Dao
interface TripDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripDay(day: TripDayEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripDays(days: List<TripDayEntity>)

    @Query("SELECT * FROM trip_days WHERE tripId = :tripId")
    suspend fun getDaysByTripId(tripId: Int): List<TripDayEntity>

    @Transaction
    @Query("SELECT * FROM trip_days WHERE id = :dayId")
    suspend fun getTripDayWithActivities(dayId: Int): TripDayWithActivities?

    @Query("DELETE FROM trip_days")
    suspend fun clearTripDays()

    @Query("DELETE FROM trip_days WHERE tripId = :tripId")
    suspend fun deleteTripDaysByTripId(tripId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayActivities(activities: List<DayActivityEntity>)
}
