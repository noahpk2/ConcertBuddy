package com.example.concertbuddy.events.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SerpApiResponse(
    val search_metadata: SearchMetadata,
    val search_parameters: SearchParameters,
    val search_information: SearchInformation,
    val events_results: List<EventResult>
)

data class SearchMetadata(
    val id: String,
    val status: String,
    val json_endpoint: String,
    val created_at: String,
    val processed_at: String,
    val google_events_url: String,
    val raw_html_file: String,
    val total_time_taken: Double
)

data class SearchParameters(
    val q: String,
    val engine: String,
    val hl: String,
    val gl: String
)

data class SearchInformation(
    val events_results_state: String
)

data class EventResult(
    val title: String,
    val date: EventDate,
    val address: List<String>,
    val link: String,
    val event_location_map: EventLocationMap,
    val description: String,
    val ticket_info: List<TicketInfo>,
    val venue: Venue,
    val thumbnail: String,
    val image: String
)

data class EventDate(
    val start_date: String,
    val `when`: String
)

data class EventLocationMap(
    val image: String,
    val link: String,
    val serpapi_link: String
)

data class TicketInfo(
    val source: String,
    val link: String,
    val link_type: String
)

data class Venue(
    val name: String,
    val reviews: Int,
    val link: String
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

