package com.example.concertbuddy.profile

import com.example.concertbuddy.events.Event
import java.io.InputStream

/**
 * Class that represents users. This class is responsible for storing the user's information, and some information of their friends. Will interact with Jetpack Rooms to store the information locally.
 * TODO: Finish implementing this class.
 */

sealed class Users {
    data class User(
        val user_id: Int,
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val attendingEvents: List<Event>,
        val hostedEvents: List<Event>,
        val interestedEvents: List<Event>,
        val location: String,
        val friends: List<Friend>,
        val interests: List<String>,
    ) {
        init {
            //init with empty
            val isLoggedIn = false
        }
        //TODO(Add profile picture)
        //TODO(implement password hashing on server)
        //TODO(Add interests as a class/tag)

    }

    data class Friend(
        val user_id: Int,
        val username: String,
        val firstName: String,
        val lastName: String,
        val attendingEvents: List<Event>,
        val hostedEvents: List<Event>,
        val interestedEvents: List<Event>,
        val location: String,
        val friends: List<Friend>,
        val interests: List<String>,
        ) {
    }

}