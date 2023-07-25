package com.example.concertbuddy.events.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SerpApiClient {



    companion object {
        private const val BASE_URL = "https://serpapi.com"

        fun create(): SerpApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SerpApiService::class.java)
        }
    }

}

