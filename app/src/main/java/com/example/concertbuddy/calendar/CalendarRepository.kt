package com.example.concertbuddy.calendar

import android.content.Context
import android.util.Log
import com.example.concertbuddy.application.ConcertBuddy.Companion.getDatabase
import com.example.concertbuddy.application.DayDao
import com.example.concertbuddy.application.LocalDatabase
import com.example.concertbuddy.events.calendarData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

/** This class is responsible for fetching and saving calendar data to the database
        |It is the "single source of truth" for the calendar */

class CalendarRepository(private val appContext: Context) {
    private val database: LocalDatabase = getDatabase(appContext)
    private val dayDao: DayDao = database.dayDao()
    private var calendarItems: MutableList<CalendarItem> = mutableListOf()
    private var events: MutableList<calendarData.Event> = mutableListOf()

    companion object {
        private const val TAG = "CalendarRepository"
    }

    init {
        // Initialize the calendarItems list
        calendarItems = getCalendarItems()
    }

    fun getCalendarItems(): MutableList<CalendarItem> {
        """This function returns a list of CalendarItems that will be used to populate the calendar
            |This includes the month headers and the date objects
        """
            .trimMargin()

        var calendarItems = mutableListOf<CalendarItem>()

        if (calendarItems.isNotEmpty()) {
            return calendarItems
        }

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        /* TODO: Add actual events */

        var month = currentMonth
        var year = currentYear

        // Repeat this chunk for 12 months
        for (i in 0 until 12) {
            if (month > 12) {
                month = 1
                year += 1
            }

            calendarItems.add(CalendarItem.MonthHeaderItem(monthToString(month), year.toString()))

            val daysInMonth = getDaysInMonth(month, year)


            calendarItems.add(CalendarItem.DateItem("", 2, emptyList()))
            // Add calendar cells for each day of the month
            for (day in 1..daysInMonth) {
                val date = "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
                calendarItems.add(CalendarItem.DateItem(date, 1, emptyList()))

                //add day to database
                val day = calendarData.Day(UUID.fromString(date), date)



            }
            Log.d(TAG, "CalendarRepository: getCalendarItems")
            // Adjust the month and year index
            month += 1
        }

        return calendarItems
    }

    fun addDayToDatabase(day: calendarData.Day){
        CoroutineScope(Dispatchers.IO).launch {
            dayDao.insertDay(day)
        }
    }
    private fun getDaysInMonth(month:Int, year:Int):Int {
        /**
         * This function returns the number of days in a given month
         */

        Log.d(TAG, "CalendarRepository: getDaysInMonth: month: $month, year: $year")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }


    private fun monthToString(month: Int):String{
        /**
         * This function returns the name of a month given the month number
         */
        return when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> "Error"
        }

    }

    fun addEvent(event: calendarData.Event) {
        /**
         * This function adds an event to the calendarItems list
         */

        //TODO: Add event to local database
        events.add(event)
        for (i in 0 until calendarItems.size) {
            if (calendarItems[i] is CalendarItem.DateItem) {
                val dateItem = calendarItems[i] as CalendarItem.DateItem
                if (dateItem.day == event.day && dateItem.month == event.month && dateItem.year == event.year) {
                    dateItem.events = dateItem.events.plusElement(event)

                }
            }
        }

    }

    fun saveEvent(event: calendarData.Event) {
        // TODO: Save an event to the database. Both local and remote.

    }
}