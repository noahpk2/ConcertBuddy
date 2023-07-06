package com.example.concertbuddy.calendar

data class Event(val name: String, val time: String, val location: String, val description: String,val date: String) {
    init {
        var name = this.name
        var time = this.time
        var location = this.location
        var description = this.description
        var date = this.date
    }
}
