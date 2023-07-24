package com.example.concertbuddy.events

import android.content.Context
import com.example.concertbuddy.application.ConcertBuddy
import com.example.concertbuddy.application.EventDao
import com.example.concertbuddy.application.LocalDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class EventRepository(private val appContext: Context) {

    private const val TICKETMASTER_BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
    private const val TICKETMASTER_API_KEY = "YOUR_TICKETMASTER_API_KEY"

}
    private val database: LocalDatabase = ConcertBuddy.getDatabase(appContext)
    private val eventDao: EventDao = database.eventDao()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://app.ticketmaster.com/discovery/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val ticketmasterApi = retrofit.create(TicketmasterApi::class.java)

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
        }



    }
}