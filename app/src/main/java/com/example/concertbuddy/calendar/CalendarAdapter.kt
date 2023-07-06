package com.example.concertbuddy.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.concertbuddy.R


class CalendarAdapter(private val items: List<CalendarItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val TAG = "CalendarAdapter"
        const val DATE_VIEW_TYPE = 1
        const val MONTH_HEADER_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATE_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_item, parent, false)
                DateViewHolder(view)
            }
            MONTH_HEADER_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_header, parent, false)
                MonthHeaderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is DateViewHolder -> {
                val dateViewHolder = holder as DateViewHolder
                val dateItem = item as CalendarItem.DateItem
                dateViewHolder.textView.text = item.day.trimStart('0')
            }
            is MonthHeaderViewHolder -> {
                val monthHeaderViewHolder = holder as MonthHeaderViewHolder
                val monthHeaderItem = item as CalendarItem.MonthHeaderItem
                monthHeaderViewHolder.textView.text = "${monthHeaderItem.month} ${monthHeaderItem.year}"
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "getItemViewType: at position: $position")
        val item = items[position]
        return if (item is CalendarItem.MonthHeaderItem){
            Log.d(TAG, "getItemViewType: MONTH_HEADER_VIEW_TYPE")
            MONTH_HEADER_VIEW_TYPE

        } else {
            Log.d(TAG, "getItemViewType: DATE_VIEW_TYPE")
            DATE_VIEW_TYPE

        }
    }


    override fun getItemCount(): Int {
        val count = items.size
        Log.d(TAG, "getItemCount: $count")
        return count
    }

    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.calendarItemTextView)
    }*/

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.calendarItemTextView)
    }

    inner class MonthHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.headerItemTextView)
    }
}