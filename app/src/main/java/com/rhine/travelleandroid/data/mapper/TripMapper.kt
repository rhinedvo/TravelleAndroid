package com.rhine.travelleandroid.data.mapper


import com.rhine.travelleandroid.data.local.entity.*
import com.rhine.travelleandroid.data.remote.dto.DayActivityDTO
import com.rhine.travelleandroid.data.remote.dto.DayActivitySaveRequest
import com.rhine.travelleandroid.data.remote.dto.Requests
import com.rhine.travelleandroid.data.remote.dto.TripDTO
import com.rhine.travelleandroid.data.remote.dto.TripDayDTO
import com.rhine.travelleandroid.data.remote.dto.TripDaySaveRequest
import com.rhine.travelleandroid.data.remote.dto.TripSaveRequest
import com.rhine.travelleandroid.domain.model.*

fun TripWithDaysAndActivities.toDomain(): Trip {
    return Trip(
        id = trip.id,
        title = trip.title,
        destination = trip.destination,
        descriptionText = trip.descriptionText,
        timeframe = trip.timeframe,
        tripStatus = trip.tripStatus,
        totalBudget = trip.totalBudget,
        aiResultsVote = trip.aiResultsVote,
        origin = trip.origin,
        estFlightCost = trip.estFlightCost,
        estAccommodationCost = trip.estAccommodationCost,
        includeEstimatedCosts = trip.includeEstimatedCosts,
        createdAt = trip.createdAt,
        updatedAt = trip.updatedAt,
        context = trip.context,
        tripDays = days.map { it.toDomain() }
    )
}
fun TripDTO.toEntity(): TripEntity = TripEntity(
    id = id,
    title = title,
    destination = destination,
    descriptionText = descriptionText,
    timeframe = timeframe,
    tripStatus = tripStatus,
    totalBudget = totalBudget,
    aiResultsVote = aiResultsVote,
    origin = origin,
    estFlightCost = estFlightCost,
    estAccommodationCost = estAccommodationCost,
    includeEstimatedCosts = includeEstimatedCosts,
    createdAt = createdAt,
    updatedAt = updatedAt,
    context = context
)

fun TripDayDTO.toEntity(tripId: Int): TripDayEntity = TripDayEntity(
    id = id,
    tripId = tripId,
    title = title,
    dayIndex = dayIndex,
    date = date,
    totalDayCost = totalDayCost,
    isEdited = isEdited
)

fun DayActivityDTO.toEntity(tripDayId: Int): DayActivityEntity = DayActivityEntity(
    id = id,
    tripDayId = tripDayId,
    dayActivitySegment = dayActivitySegment,
    name = name,
    details = details,
    cost = cost,
    externalUrl = externalUrl
)
fun TripDayWithActivities.toDomain(): TripDay {
    return TripDay(
        id = day.id,
        title = day.title,
        dayIndex = day.dayIndex,
        date = day.date.orEmpty(),
        totalDayCost = day.totalDayCost ?: 0.0,
        isEdited = day.isEdited,
        dayActivities = activities.map { it.toDomain() }
    )
}

fun DayActivityEntity.toDomain(): DayActivity {
    return DayActivity(
        id = id,
        dayActivitySegment = dayActivitySegment,
        name = name,
        details = details,
        cost = cost,
        externalUrl = externalUrl
    )
}
fun Requests.toDomain(): Request {
    return Request(
        destination = destination.orEmpty(),
        budget = budget ?: 0.0,
        context = context.orEmpty(),
        timeframe = timeframe.orEmpty(),
        origin = origin.orEmpty(),
        includeEstimatedCosts = includeEstimatedCosts ?: false
    )
}

fun Request.toDto(): Requests {
    return Requests(
        destination = destination,
        budget = budget,
        context = context,
        timeframe = timeframe,
        origin = origin,
        includeEstimatedCosts = includeEstimatedCosts
    )
}
fun Trip.toSaveRequest(): Requests {
    return Requests(
        destination = destination,
        budget = totalBudget,
        context = context,
        timeframe = timeframe,
        origin = origin,
        includeEstimatedCosts = includeEstimatedCosts
    )
}
fun TripDTO.toDomain(): Trip {
    return Trip(
        id = id,
        title = title,
        destination = destination,
        descriptionText = descriptionText,
        timeframe = timeframe,
        tripStatus = tripStatus,
        totalBudget = totalBudget,
        aiResultsVote = aiResultsVote,
        origin = origin,
        estFlightCost = estFlightCost,
        estAccommodationCost = estAccommodationCost,
        includeEstimatedCosts = includeEstimatedCosts,
        createdAt = createdAt,
        updatedAt = updatedAt,
        context = context,
        tripDays = tripDays.map { it.toDomain() }
    )
}

fun TripDayDTO.toDomain(): TripDay {
    return TripDay(
        id = id,
        title = title,
        dayIndex = dayIndex,
        date = date.orEmpty(),
        totalDayCost = totalDayCost ?: 0.0,
        isEdited = isEdited,
        dayActivities = dayActivities.map { it.toDomain() }
    )
}

fun DayActivityDTO.toDomain(): DayActivity {
    return DayActivity(
        id = id,
        dayActivitySegment = dayActivitySegment,
        name = name,
        details = details,
        cost = cost,
        externalUrl = externalUrl
    )
}
fun Trip.toTripSaveRequest(): TripSaveRequest {
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
            TripDaySaveRequest(
                day = day.dayIndex,
                date = day.date,
                dayTitle = day.title,
                morning = day.dayActivities.getOrNull(0)?.let {
                    DayActivitySaveRequest(
                        name = it.name,
                        details = it.details,
                        cost = it.cost,
                        link = it.externalUrl
                    )
                },
                afternoon = day.dayActivities.getOrNull(1)?.let {
                    DayActivitySaveRequest(
                        name = it.name,
                        details = it.details,
                        cost = it.cost,
                        link = it.externalUrl
                    )
                },
                evening = day.dayActivities.getOrNull(2)?.let {
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