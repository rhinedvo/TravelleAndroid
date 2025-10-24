package com.rhine.travelleandroid.data.local.dao
import androidx.room.*
import com.rhine.travelleandroid.data.local.entity.*

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(trips: List<TripEntity>)

    @Query("SELECT * FROM trips")
    suspend fun getAllTrips(): List<TripEntity>

    @Transaction
    @Query("SELECT * FROM trips")
    suspend fun getAllTripsWithRelations(): List<TripWithDaysAndActivities>

    @Transaction
    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripWithRelationsById(tripId: Int): TripWithDaysAndActivities?

    @Query("DELETE FROM trips")
    suspend fun clearTrips()

    @Query("DELETE FROM trips WHERE id = :id")
    suspend fun deleteTripById(id: Int)

}