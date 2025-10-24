package com.rhine.travelleandroid.domain.model

import com.rhine.travelleandroid.data.remote.dto.*

data class Trip(
    val id: Int,
    val title: String,
    val destination: String,
    val descriptionText: String,
    val timeframe: String,
    val tripStatus: String,
    val totalBudget: Double,
    val aiResultsVote: String,
    val origin: String,
    val estFlightCost: Double,
    val estAccommodationCost: Double,
    val includeEstimatedCosts: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val context: String,
    val tripDays: List<TripDay>
)

data class TripDay(
    val id: Int,
    val title: String,
    val dayIndex: Int,
    val date: String,
    val totalDayCost: Double,
    val isEdited: Boolean,
    val dayActivities: List<DayActivity> = emptyList()
)

data class DayActivity(
    val id: Int,
    val dayActivitySegment: String,
    val name: String,
    val details: String?,
    val cost: Double?,
    val externalUrl: String?
)

fun Trip.toSaveRequest(): TripSaveRequest {
    return TripSaveRequest(
        tripName = title,
        description = descriptionText,
        destination = destination,
        budget = totalBudget,
        context = context,
        timeframe = timeframe,
        estFlightCost = estFlightCost,
        estAccommodationCost = estAccommodationCost,
        origin = origin,
        includeEstimatedCosts = includeEstimatedCosts,
        days = tripDays.map { day ->
            val morningActivity = day.dayActivities.find { it.dayActivitySegment.equals("morning", ignoreCase = true) }
            val afternoonActivity = day.dayActivities.find { it.dayActivitySegment.equals("afternoon", ignoreCase = true) }
            val eveningActivity = day.dayActivities.find { it.dayActivitySegment.equals("evening", ignoreCase = true) }

            TripDaySaveRequest(
                day = day.dayIndex,
                date = day.date,
                dayTitle = day.title,
                morning = morningActivity?.let {
                    DayActivitySaveRequest(
                        name = it.name,
                        details = it.details,
                        cost = it.cost,
                        link = it.externalUrl
                    )
                },
                afternoon = afternoonActivity?.let {
                    DayActivitySaveRequest(
                        name = it.name,
                        details = it.details,
                        cost = it.cost,
                        link = it.externalUrl
                    )
                },
                evening = eveningActivity?.let {
                    DayActivitySaveRequest(
                        name = it.name,
                        details = it.details,
                        cost = it.cost,
                        link = it.externalUrl
                    )
                },
                totalDayCost = day.totalDayCost
            )
        }
    )
}