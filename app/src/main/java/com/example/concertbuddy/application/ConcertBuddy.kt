package com.example.concertbuddy.application

import android.app.Application

/**
 * Top level application class. Things that live in here persist throughout the entire application. Using Jetpack Rooms, we can store data in a local database that will persist even if the app is closed.
 *
 */
class ConcertBuddy: Application() {


    override fun onCreate() {
        super.onCreate()

    }
}