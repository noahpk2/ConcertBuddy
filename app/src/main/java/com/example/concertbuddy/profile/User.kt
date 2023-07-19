package com.example.concertbuddy.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.concertbuddy.events.calendarData
import java.util.UUID

sealed class Users {

    @Entity(tableName = "users")
    data class User(
        @PrimaryKey val user_id: UUID,
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val attendingEvents: List<UUID>, // Use TypeConverters to convert between List<UUID> and String
        val hostedEvents: List<UUID>,    // Use TypeConverters
        val interestedEvents: List<UUID>,// Use TypeConverters
        val location: String,
        val friends: List<UUID>,         // Use TypeConverters
        val interests: String,       // Use TypeConverters
        val isLoggedIn: Boolean,
    )

    @Entity(tableName = "friends")
    data class Friend(
        @PrimaryKey val user_id: UUID,
        val username: String,
        val firstName: String,
        val lastName: String,
        val attendingEvents: List<UUID>, // Use TypeConverters
        val hostedEvents: List<UUID>,    // Use TypeConverters
        val interestedEvents: List<UUID>,// Use TypeConverters
        val location: String,
        val friends: List<UUID>,         // Use TypeConverters
        val interests: String        // Use TypeConverters
    )
}