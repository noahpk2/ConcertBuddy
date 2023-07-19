package com.example.concertbuddy.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.UUID

sealed class calendarData {
    @Entity(
        tableName = "events",
        foreignKeys = [

        ]
    )
    data class Event(
        @PrimaryKey val event_id: UUID, val day_id: UUID,
        val title: String, val time: String, val location: String, val description: String,
        val date: String
    ) {

        var day: String
        var month: String
        var year: String 

        init {
        day = date.split("/")[1]
        month = date.split("/")[0]
        year = date.split("/")[2]

        }
    }
    @Entity(tableName = "days")
    data class Day(
        @PrimaryKey
        val day_id: UUID,
        val date: String
    )


}

