package com.example.concertbuddy.events.data

import android.content.Context
import android.util.Log
import com.example.concertbuddy.BuildConfig
import com.example.concertbuddy.application.ConcertBuddy
import com.example.concertbuddy.application.EventDao
import com.example.concertbuddy.application.LocalDatabase
import com.example.concertbuddy.calendar.CalendarData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

/**
 * <h1>EventRepository</h1>
 * <p>Event Repository for Fetching Data from API's, translating to event struct, and storing in local database</p>
 */
class EventRepository(private val appContext: Context, private val SerpApiService: SerpApiService) {

    companion object {
        private const val TAG = "EventRepository"
    }

    private val database: LocalDatabase = ConcertBuddy.getDatabase(appContext)
    private val eventDao: EventDao = database.eventDao()
    private val dayDao = database.dayDao()
    private fun getDefaultParameters(): HashMap<String, String> {
        val key = BuildConfig.MY_API_KEY
        return hashMapOf(
            "q" to "Concerts in Denver",
            "engine" to "google_events",
            "hl" to "en",
            "gl" to "us",
            "api_key" to "$key"
        )
    }

    /**
     * init: if the database is empty, fetch data from API's and store in database
     */
    init {
        Log.d(TAG, "init: ")
        CoroutineScope(Dispatchers.IO).launch {
            if (eventDao.getAllEvents().isEmpty()) {
                Log.d(TAG, "init: database is empty")
                val response = getSearchResults()
                if (response != null) {
                    val events = transformToEventList(response)
                    addListEvents(events)
                }
            }
        }

    }



    private fun getSearchResults(): SerpApiResponse? {
        Log.d(TAG, "getSearchResults:  ")
        val call = SerpApiService.getResults(getDefaultParameters())
        val response = call.execute()
        return if (response.isSuccessful) {
            Log.d(TAG, "getSearchResults: response is successful")
            response.body()
        } else {
            null
        }
    }

    suspend fun getEvents(): MutableList<CalendarData.Event> {
        Log.d(TAG, "getEvents: ")
        return eventDao.getAllEvents()
    }

    suspend fun addEvent(event: CalendarData.Event) {
        eventDao.insertEvent(event)
    }

    private fun transformDateFormat(input: String): String? {
        val sourceFormat = SimpleDateFormat("MMM/dd/yyyy", Locale.US)
        val targetFormat = SimpleDateFormat("yyyy-M-d", Locale.US)

        return try {
            val date = sourceFormat.parse(input)
            targetFormat.format(date)
        } catch (e: Exception) {
            null // or handle the exception as needed
        }
    }

    private suspend fun addListEvents(events: List<CalendarData.Event>) {

        for (event in events) {
            // Check if the day already exists in the database.
            // If it does not, add it to the database.
            Log.d(TAG, "addListEvents: date:${event.date}")
            val day = dayDao.getDayByDate(event.date)
            Log.d(TAG, "addListEvents: day = $day")
            if (day == null) {

            }

                eventDao.insertEvent(event)

        }

    }
    /**
     * <h1>translateResults</h1>
     * <p>Translates the results from the API's to the event struct</p>
     */
    private suspend fun transformToEventList(response: SerpApiResponse): List<CalendarData.Event> {
        Log.d(TAG, "transformToEventList: Start")
        return response.events_results.map { eventResult ->
            val eventId = UUID.randomUUID()  // Generate a unique event ID.
            val date = transformDateFormat(extractDateFromWhen(eventResult.date.`when`)).toString()
            // Check if the day already exists in the database.
            // If it does not, add it to the database.
            val day = dayDao.getDayByDate(date)
            Log.d(TAG, "transformToEventList: eventId = $eventId")
            Log.d(TAG, "transformToEventList: day = $day")
            Log.d(TAG, "transformToEventList: date = $date")


            val dayId = if (day == null) {
                Log.d(TAG, "transformToEventList: day is null")
                val newDayId = UUID.randomUUID()
                dayDao.insertDay(CalendarData.Day(newDayId, date))
                newDayId
            } else {
                day.day_id
            }

            Log.d(TAG, "transformToEventList: dayId = $dayId")



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
    private suspend fun extractDateFromWhen(whenString: String): String {
        val parts = whenString.split(", ")
        val startDate = parts[1].split(" ")
        val month = startDate[0]
        val day = startDate[1]
        // Note: This assumes the current year since the year is not provided in the example JSON.
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        return "$month/$day/$year"
    }

    // Helper function to extract the time range from the 'when' string.
    private fun extractTimeFromWhen(whenString: String): String {
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



