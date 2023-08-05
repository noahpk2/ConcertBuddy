package com.example.concertbuddy.calendar

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.concertbuddy.application.ConcertBuddy.Companion.getDatabase
import com.example.concertbuddy.application.DayDao
import com.example.concertbuddy.application.EventDao
import com.example.concertbuddy.application.LocalDatabase
import com.example.concertbuddy.events.data.EventRepository
import com.example.concertbuddy.events.data.SerpApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID

/** This class is responsible for fetching and saving calendar data to the database
|It is the "single source of truth" for the calendar */

class CalendarRepository(private val appContext: Context) {
    private val database: LocalDatabase = getDatabase(appContext)
    private val dayDao: DayDao = database.dayDao()
    private val eventDao = database.eventDao()
    private var _calendarItems: MutableLiveData<MutableList<CalendarItem>> = MutableLiveData(mutableListOf())

    val calendarItems: LiveData<MutableList<CalendarItem>>
        get() = _calendarItems

    init {
        loadCalendarItems()
    }

    private fun loadCalendarItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getCalendarItems()
            if(result != null) {
                _calendarItems.postValue(result)
            } else {
                _calendarItems.postValue(mutableListOf())
            }
        }
    }


    /**This function returns a list of CalendarItems that will be used to populate the calendar.
    * This includes the month headers and the date objects
    */
    suspend fun getCalendarItems(): MutableList<CalendarItem> {
        val currentCalendarItems = _calendarItems.value ?: mutableListOf()

        if (currentCalendarItems.isNotEmpty()) {
            return currentCalendarItems
        }

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        var month = currentMonth
        var year = currentYear

        // Repeat this chunk for 12 months
        for (i in 0 until 12) {
            if (month > 12) {
                month = 1
                year += 1
            }

            currentCalendarItems.add(CalendarItem.MonthHeaderItem(monthToString(month), year.toString()))

            val daysInMonth = getDaysInMonth(month, year, TAG)

            currentCalendarItems.add(CalendarItem.DateItem("", 2, emptyList()))

            // Add calendar cells for each day of the month
            for (day in 1..daysInMonth) {
                val date = "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
                val eventsForDay = eventDao.getEventsForDay(date)
                currentCalendarItems.add(CalendarItem.DateItem(date, 1, emptyList()))

                // Add day to database
                val dayItem = CalendarData.Day(UUID.randomUUID(), date)
                Log.d(TAG, "CalendarRepository: getCalendarItems: date format: $date")
                addDayToDatabase(dayItem)
            }

            Log.d(TAG, "CalendarRepository: getCalendarItems")
            month += 1
        }

        return currentCalendarItems
    }

    fun addDayToDatabase(day: CalendarData.Day) {
        CoroutineScope(Dispatchers.IO).launch {
            dayDao.insertDay(day)
        }
    }

    private fun getDaysInMonth(month: Int, year: Int, tag: String): Int {
        /**
         * This function returns the number of days in a given month
         */

        Log.d(tag, "CalendarRepository: getDaysInMonth: month: $month, year: $year")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }


    private fun monthToString(month: Int): String {
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

    fun addEvent(event: CalendarData.Event) {
        /**
         * This function adds an event to the calendarItems list
         */

        //TODO: Add event to local database | Also, more efficient way to do this?

        val currentCalendarItems = _calendarItems.value ?: mutableListOf()
        for (i in 0 until currentCalendarItems.size) {
            if (currentCalendarItems[i] is CalendarItem.DateItem) {
                val dateItem = currentCalendarItems[i] as CalendarItem.DateItem
                if (dateItem.day == event.day && dateItem.month == event.month && dateItem.year == event.year) {
                    dateItem.events = dateItem.events.plusElement(event)
                }
            }
        }
        _calendarItems.postValue(currentCalendarItems)
    }

    fun saveEvent(event: CalendarData.Event) {
        // TODO: Save an event to the database. Both local and remote.

        // Add the event to the appropriate DateItem
        val currentCalendarItems = _calendarItems.value ?: mutableListOf()
        for (i in 0 until currentCalendarItems.size) {
            if (currentCalendarItems[i] is CalendarItem.DateItem) {
                val dateItem = currentCalendarItems[i] as CalendarItem.DateItem
                if (dateItem.day == event.day && dateItem.month == event.month && dateItem.year == event.year) {
                    dateItem.events = dateItem.events.plusElement(event)
                }
            }
        }
        _calendarItems.postValue(currentCalendarItems)

        // TODO: Insert the code for saving event to remote and local database
    }
companion object {
    private const val TAG = "CalendarRepository"

    //  Singleton instantiation
    @Volatile
    private var INSTANCE: CalendarRepository? = null

    fun getInstance(appContext: Context): CalendarRepository {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: CalendarRepository(appContext).also {
                INSTANCE = it
            }
        }
    }
}

}
