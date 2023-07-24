package com.example.concertbuddy.events

data class DiscoveryAPI(
    val id: String,
    val name: String,
    val url: String,
    val dates: EventDates,
    val images: List<EventImage>,
    val _embedded: EventEmbedded?
)

data class EventDates(
    val start: EventDateStart
)

data class EventDateStart(
    val localDate: String
)

data class EventImage(
    val url: String,
    val width: Int,
    val height: Int
)

data class EventEmbedded(
    val venues: List<EventVenue>
)

data class EventVenue(
    val name: String,
    val location: EventLocation
)

data class EventLocation(
    val latitude: Double,
    val longitude: Double
)

