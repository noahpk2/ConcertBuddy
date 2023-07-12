package com.example.concertbuddy.profile

import com.example.concertbuddy.events.Event

data class User(val id : Int,
                val username : String,
                val email: String,
                val firstName: String,
                val lastName : String,
                val attendingEvents : List<Event>,
                val hostedEvents : List<Event>,
                val interestedEvents: List<Event>,
                val location: String,
                val friends: List<User>,
                val interests: List<String>,) {
    init
    {
        //init with empty
        val isLoggedIn = false
    }
    //TODO(Add profile picture)
    //TODO(implement password hashing on server)
    //TODO(Add friends list)
    //TODO(Add location)
    //TODO(Add interests as a class/tag)
}