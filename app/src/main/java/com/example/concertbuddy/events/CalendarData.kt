package com.example.concertbuddy.events

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

sealed class CalendarData {
    @Entity(
        tableName = "events",
        foreignKeys = [
            ForeignKey(
                entity = Day::class,
                parentColumns = ["day_id"],
                childColumns = ["day_id"],
                onDelete = ForeignKey.CASCADE
            )
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
            val parts = date.split("/")
            month = parts[0]
            day = parts[1]
            year = parts[2]
        }
    }
    @Entity(tableName = "days")
    data class Day(
        @PrimaryKey
        val day_id: UUID,
        val date: String
    )


}

