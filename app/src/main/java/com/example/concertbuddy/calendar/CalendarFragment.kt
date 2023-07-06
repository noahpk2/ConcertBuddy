package com.example.concertbuddy.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concertbuddy.R
import java.util.Calendar


class CalendarFragment : Fragment() {
    companion object {
        private const val TAG = "CalendarFragment"
    }
    private lateinit var recyclerView: RecyclerView
    private var calendarAdapter: CalendarAdapter? = null
    private var calendarItems: MutableList<CalendarItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the calendarItems list
        calendarItems = getCalendarItems()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)
        recyclerView = rootView.findViewById(R.id.month_calendar)
        calendarAdapter = CalendarAdapter(getCalendarItems())

        val layoutManager = GridLayoutManager(requireContext(), 7)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (calendarAdapter?.getItemViewType(position) == CalendarAdapter.MONTH_HEADER_VIEW_TYPE) {
                    7 // Use the full span count for month headers
                } else {
                    1 // Use a span count of 1 for date items
                }
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = calendarAdapter

        return rootView
    }

    private fun getCalendarItems(): MutableList<CalendarItem> {
        """This function returns a list of CalendarItems that will be used to populate the calendar
            |This includes the month headers and the date objects
        """
            .trimMargin()

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

            //This code was to align the first day of the month but it actually looks better without it

            //val startDayOfWeek = getStartDayOfWeek(month, year)

            // Add empty days before the first day of the month
           /* var spanCount : Int = 0
            for (j in 1 until startDayOfWeek) {
                spanCount += 1
            }*/


            calendarItems.add(CalendarItem.DateItem("", 2, emptyList()))
            // Add calendar cells for each day of the month
            for (day in 1..daysInMonth) {
                val date = "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
                calendarItems.add(CalendarItem.DateItem(date, 1, emptyList()))
                Log.d(TAG, "CalendarFragment: getCalendarItems: date: $date")
            }

            // Adjust the month and year index
            month += 1
        }

        return calendarItems
    }


    private fun getDaysInMonth(month:Int, year:Int):Int {
        """This function returns the number of days in a given month"""
        Log.d(TAG, "CalendarFragment: getDaysInMonth: ")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun getStartDayOfWeek(month: Int, year: Int): Int {
        """Dont really need this anymore"""
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1) // Set the calendar to the first day of the month
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    private fun monthToString(month: Int):String{
        """Converts the month number to a string"""
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

}