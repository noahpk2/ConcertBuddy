package com.example.concertbuddy.events.data

import android.app.appsearch.SearchResults
import android.content.Context
import com.example.concertbuddy.application.ConcertBuddy
import com.example.concertbuddy.application.EventDao
import com.example.concertbuddy.application.LocalDatabase
import com.example.concertbuddy.events.CalendarData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/**
 * <h1>EventRepository</h1>
 * <p>Event Repository for Fetching Data from API's, translating to event struct, and storing in local database</p>
 */
class EventRepository(private val appContext: Context, private val SerpApiService: SerpApiService) {


    private val database: LocalDatabase = ConcertBuddy.getDatabase(appContext)
    private val eventDao: EventDao = database.eventDao()


    fun getSearchResults(parameters: HashMap<String,String>): SerpApiResponse? {
        val call = SerpApiService.getResults(parameters)
        val response = call.execute()

        return if (response.isSuccessful) {
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<SerpApiResponse> =
                moshi.adapter(SerpApiResponse::class.java)
            val jsonString = response.body().toString()
            return jsonAdapter.fromJson(jsonString)


        } else {
            null
        }
    }

    suspend fun getEvents(): MutableList<CalendarData.Event> {
        return eventDao.getAllEvents()
    }


    /**
     * <h1>translateResults</h1>
     * <p>Translates the results from the API's to the event struct</p>
     */
    fun translateResults(serpApiResponse: SerpApiResponse){


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



