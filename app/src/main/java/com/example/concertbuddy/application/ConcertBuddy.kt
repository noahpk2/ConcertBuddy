package com.example.concertbuddy.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room

/**
 * Top level application class. Things that live in here persist throughout the entire application. Using Jetpack Rooms, we can store data in a local database that will persist even if the app is closed.
 *
 */
@Suppress("UNREACHABLE_CODE")
class ConcertBuddy: Application() {
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        private var PREFS_INSTANCE: SharedPreferences? = null
        private const val IS_LOGGED_IN = "is_logged_in"

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

        fun getPreferences(context: Context): SharedPreferences {
            return PREFS_INSTANCE ?: synchronized(this) {
                val prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                PREFS_INSTANCE = prefs
                prefs
            }
        }

        fun isLoggedIn(context: Context): Boolean {
            return getPreferences(context).getBoolean(IS_LOGGED_IN, false)
        }

        fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
            getPreferences(context).edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply()
        }
    }

    override fun onCreate() {
        super.onCreate()
        getDatabase(this)
        getPreferences(this)
    }
}
