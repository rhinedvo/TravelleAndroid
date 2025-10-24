package com.rhine.travelleandroid.data.remote.api

import com.rhine.travelleandroid.data.remote.dto.BaseResponse
import com.rhine.travelleandroid.data.remote.dto.TripDTO
import com.rhine.travelleandroid.data.remote.dto.TripSaveRequest
import com.rhine.travelleandroid.data.remote.dto.Requests
import com.rhine.travelleandroid.data.remote.dto.TripSurpriseResponse
import retrofit2.http.*

interface TripsAPI {
    @GET("/api/trip/{id}")
    suspend fun getTripId(@Path("id") tripId: Int): BaseResponse

    @DELETE("/api/trip/{id}/delete")
    suspend fun tripDelete(@Path("id") tripId: Int): BaseResponse

    @POST("/api/trip/{trip_id}/regenerate")
    suspend fun tripRegenerate(@Path("trip_id") tripId: Int): BaseResponse

    @FormUrlEncoded
    @POST("/api/trip/{trip_id}/vote")
    suspend fun tripVote(
        @Path("trip_id") tripId: Int,
        @Field("ai_results_vote") aiResultsVote: String
    ): BaseResponse

    @POST("/api/trip/create")
    suspend fun tripCreate(@Body request: Requests?): BaseResponse

    @POST("/api/trip/{trip_id}/edit")
    suspend fun tripEdit(@Path("trip_id") tripId: Int, @Body request: Requests?): BaseResponse

    @POST("/api/ai/surprise")
    suspend fun tripSurprise(@Body request: Requests?): TripSurpriseResponse

    @POST("/api/ai/surprise/save")
    suspend fun tripSurpriseSave(@Body request: TripSaveRequest?): BaseResponse

    @FormUrlEncoded
    @POST("/api/trip/day/{trip_day_id}/edit")
    suspend fun tripDayEdit(
        @Path("trip_day_id") tripDayId: Int,
        @Field("destination") destination: String,
        @Field("budget") budget: Double,
        @Field("context") context: String
    ): BaseResponse

    @POST("/api/trip/day/{trip_day_id}/regenerate")
    suspend fun tripDayRegenerate(@Path("trip_day_id") tripDayId: Int): BaseResponse

    @FormUrlEncoded
    @POST("/api/trip/day/activity/{day_activity_id}/edit")
    suspend fun tripDayActivityEdit(
        @Path("day_activity_id") dayActivityId: Int,
        @Field("context") context: String
    ): BaseResponse

    @GET("/api/trip/list")
    suspend fun getTrips(): BaseResponse

    @POST("/trips/sync")
    suspend fun tripSync(@Body trips: List<TripDTO>): BaseResponse
}