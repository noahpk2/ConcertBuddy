package com.example.concertbuddy.events.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SerpApiService{
    @GET("search.json?engine=google_events")
    fun getResults(
        @QueryMap parameters: HashMap<String,String>
    ): Call<SerpApiResponse>
}