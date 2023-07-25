package com.example.concertbuddy.events.data

import android.content.Context
import com.example.concertbuddy.application.ConcertBuddy
import com.example.concertbuddy.application.EventDao
import com.example.concertbuddy.application.LocalDatabase
import com.example.concertbuddy.calendar.CalendarData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

/**
 * <h1>EventRepository</h1>
 * <p>Event Repository for Fetching Data from API's, translating to event struct, and storing in local database</p>
 */
class EventRepository(private val appContext: Context, private val SerpApiService: SerpApiService) {


    private val database: LocalDatabase = ConcertBuddy.getDatabase(appContext)
    private val eventDao: EventDao = database.eventDao()
    private fun getDefaultParameters(): HashMap<String, String> {
        return hashMapOf(
            "q" to "Concerts in Denver",
            "engine" to "google_events",
            "hl" to "en",
            "gl" to "us"
        )
    }

    /**
     * init: if the database is empty, fetch data from API's and store in database
     */
    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (eventDao.getAllEvents().isEmpty()) {
                val response = getSearchResults(getDefaultParameters())
                if (response != null) {
                    val events = transformToEventList(response)
                    addListEvents(events)
                }
            }
        }

    }



    fun getSearchResults(parameters: HashMap<String, String>): SerpApiResponse? {
        val call = SerpApiService.getResults(getDefaultParameters())
        val response = call.execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getEvents(): MutableList<CalendarData.Event> {
        return eventDao.getAllEvents()
    }

    suspend fun addEvent(event: CalendarData.Event) {
        eventDao.insertEvent(event)
    }

    suspend fun addListEvents(events: List<CalendarData.Event>) {
        for (event in events) {
            eventDao.insertEvent(event)
        }
    }
    /**
     * <h1>translateResults</h1>
     * <p>Translates the results from the API's to the event struct</p>
     */
    fun transformToEventList(response: SerpApiResponse): List<CalendarData.Event> {
        return response.events_results.map { eventResult ->
            val eventId = UUID.randomUUID()  // Generate a unique event ID.
            val dayId = UUID.randomUUID()    // Generate a unique day ID.
            val date = extractDateFromWhen(eventResult.date.`when`)

            CalendarData.Event(
                event_id = eventId,
                day_id = dayId,
                title = eventResult.title,
                time = extractTimeFromWhen(eventResult.date.`when`),
                location = eventResult.address.joinToString(", "),
                description = eventResult.description,
                date = date
            )
        }
    }


    // Helper function to extract date in MM/DD/YYYY format from the 'when' string.
// You can modify this to fit the exact date format provided by the API.
    fun extractDateFromWhen(whenString: String): String {
        val parts = whenString.split(", ")
        val startDate = parts[1].split(" ")
        val month = startDate[0]
        val day = startDate[1]
        // Note: This assumes the current year since the year is not provided in the example JSON.
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        return "$month/$day/$year"
    }

    // Helper function to extract the time range from the 'when' string.
    fun extractTimeFromWhen(whenString: String): String {
        val parts = whenString.split(" – ")
        return if (parts.size == 2) "${parts[0].split(", ").last()} – ${
            parts[1].split(", ").first()
        }" else ""
    }
}



    /*companion object {
            // TICKETMASTER API
            private const val TICKETMASTER_BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
            private const val TICKETMASTER_API_KEY = "YOUR_TICKETMASTER_API_KEY"

            //MOVED THESE TO SEPARATE CLASSES


        }*/

    /*  private val retrofit_ticketmaster = Retrofit.Builder()
        .baseUrl("https://app.ticketmaster.com/discovery/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit_serp = Retrofit.Builder()
        .baseUrl("https://serpapi.com/search.json")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
*/

    /*private val ticketmasterApi = retrofit.create(TicketmasterApi::class.java)

    interface TicketMasterApiService {
        //define events
        //time
        //location

        suspend fun getEventsFromTicketmaster(keyword: String, location: String): List<TicketmasterEvent>? {
            try {
                val response = ticketmasterApiService.getEvents(
                    apiKey = TICKETMASTER_API_KEY,
                    keyword = keyword,
                    location = location,
                    radius = "15" // 15 miles
                ).execute()

                if (response.isSuccessful) {
                    val data = response.body()
                    return data?._embedded?.events
                } else {

                    return null
                }
            } catch (e: Exception) {

                return null
            }
        }*/



