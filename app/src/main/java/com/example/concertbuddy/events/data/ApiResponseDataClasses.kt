package com.example.concertbuddy.events.data

data class SerpApiResponse(
    val title: String,
    val date: String,
    val address: String,
    val time: String,
    val venue: String,
    val link: String,
    val link_type: String,
    val description: String,
    val image: String,

)



data class TicketmasterApiResponse(
    val title: String
)

data class AXSApiResponse(
    val title: String
)

data class RustServerApiResponse(
    val title: String
)

