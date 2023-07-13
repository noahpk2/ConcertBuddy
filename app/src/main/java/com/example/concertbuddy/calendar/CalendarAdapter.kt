package com.example.concertbuddy.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.concertbuddy.R

/**
 * Adapter for the calendar recycler view. This adapter is responsible for displaying the calendar items in the recycler view.
 *
 */
class CalendarAdapter(emptyList: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: MutableList<CalendarItem> = mutableListOf()
    companion object {
        private const val TAG = "CalendarAdapter"
        const val DATE_VIEW_TYPE = 1
        const val MONTH_HEADER_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /**
         * Create a new view holder depending on the view type. The view type is determined by the getItemViewType method.
         * in the list of calendar items, we have 2 types:
         * 1. DateItem - this is a date item that represents a date in the calendar
         * 2. MonthHeaderItem - this is a month header that covers the entire span of the recycler view
         */
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
        /**
         * Bind the view holder depending on the view type. The view type is determined by the getItemViewType method.
         *
         */

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
        /**
         * Get the view type of the item at the given position. The view type is determined by the type of the item.
         *
         */
        //Log.d(TAG, "getItemViewType: at position: $position")
        val item = items[position]
        return if (item is CalendarItem.MonthHeaderItem){
            //Log.d(TAG, "getItemViewType: MONTH_HEADER_VIEW_TYPE")
            MONTH_HEADER_VIEW_TYPE

        } else {
            //Log.d(TAG, "getItemViewType: DATE_VIEW_TYPE")
            DATE_VIEW_TYPE

        }
    }

    fun updateItems(newItems: List<CalendarItem>) {
        /**
         * Update the items in the recycler view with the new items.
         * This happens when the user scrolls the calendar and the new items are loaded.
         */
        Log.d(TAG, "updateItems: ")
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        /**
         * Get the number of items in the recycler view.
         *
         */
        //Log.d(TAG, "getItemCount: $count")
        return items.size
    }

    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.calendarItemTextView)
    }*/

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * View holder for the date item.
         *
         */
        val textView: TextView = itemView.findViewById(R.id.calendarItemTextView)
    }

    inner class MonthHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * View holder for the month header item.
         *
         */
        val textView: TextView = itemView.findViewById(R.id.headerItemTextView)
    }
}