package com.example.concertbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar



class CalendarFragment : Fragment() {
    companion object {
        private const val TAG = "CalendarFragment"
    }
    private lateinit var recyclerView: RecyclerView
    private var calendarAdapter: CalendarAdapter? = null
    private lateinit var calendarItems: List<CalendarItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "CalendarFragment: onCreateView: ")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        calendarItems = getCalendarItems()
        calendarAdapter = CalendarAdapter(calendarItems)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        recyclerView.adapter = calendarAdapter
        return view
    }

    private fun getCalendarItems(): List<CalendarItem> {
        Log.d(TAG, "CalendarFragment: getCalendarItems: ")
        val items = mutableListOf<CalendarItem>()
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH)

        /* TODO("add actual events") */

        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val daysInMonth = getDaysInMonth(month, year)
        val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        //add empty days before first day of month
        for (i in 1 until startDayOfWeek) {
            items.add(CalendarItem("", emptyList()))
        }
        //add calendar cells for each day of month
        for (day in 1..daysInMonth) {
            val date = "$year-${month + 1}-$day"
            items.add(CalendarItem(date, emptyList()))
        }
        return items

    }

    private fun getDaysInMonth(month:Int, year:Int):Int {
        Log.d(TAG, "CalendarFragment: getDaysInMonth: ")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }


}