package com.example.concertbuddy.calendar

import com.example.concertbuddy.events.Event


sealed class CalendarItem {
    data class DateItem(val date: String, val span: Int, var events: List<Event>) : CalendarItem(){
        var month: String = ""
        var day: String = ""
        var year: String = ""

        init {
            if (!date.isEmpty()) {
                val dateParts = date.split("-")
                month = when (dateParts[1]) {
                    "01" -> "January"
                    "02" -> "February"
                    "03" -> "March"
                    "04" -> "April"
                    "05" -> "May"
                    "06" -> "June"
                    "07" -> "July"
                    "08" -> "August"
                    "09" -> "September"
                    "10" -> "October"
                    "11" -> "November"
                    "12" -> "December"
                    else -> "Error"
                }
                day = dateParts[2]
                year = dateParts[0]
                var date = this.date
            }
        }
}
    data class MonthHeaderItem(val month: String, val year: String) : CalendarItem()
}

