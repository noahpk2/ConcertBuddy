package com.example.concertbuddy.application

import android.app.Application
import android.content.Context
import androidx.room.Room

/**
 * Top level application class. Things that live in here persist throughout the entire application. Using Jetpack Rooms, we can store data in a local database that will persist even if the app is closed.
 *
 */
class ConcertBuddy: Application() {
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database_name"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        getDatabase(this)


    }
}