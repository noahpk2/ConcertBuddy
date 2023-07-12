package com.example.concertbuddy.calendar

import android.widget.DatePicker

data class Event(
    val title: String, val time: String, val location: String, val description: String,
    val date: String) {

    val day: String = date.split("/")[1]
    val month: String = date.split("/")[0]
    val year: String = date.split("/")[2]



    init {

    }


}
