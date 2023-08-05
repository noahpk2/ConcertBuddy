package com.example.concertbuddy.events

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.concertbuddy.R
import com.example.concertbuddy.calendar.CalendarData
import java.util.UUID

/**
 * Adapter for the calendar recycler view. This adapter is responsible for displaying the calendar items in the recycler view.
 *
 */
class ListEventsAdapter(private val listener: OnItemClickListener, emptyList: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: MutableList<CalendarData.Event> = mutableListOf()

    companion object {
        private const val TAG = "ListEventsAdapter"

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_view, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = items[position]
        when(holder) {
            is EventViewHolder -> {
                val eventItem = event as CalendarData.Event
                holder.title.text = event.title  // Assuming 'title' is a property of CalendarData.Event
                holder.date.text = event.date  // Assuming 'date' is a property of CalendarData.Event
            }

        }

    }



    fun updateItems(newItems: List<CalendarData.Event>) {
        /**
         * Update the items in the recycler view with the new items.
         * This happens when the user scrolls the list and the new items are loaded.
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


    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * View holder for the event item.
         *
         */
        val title: TextView = itemView.findViewById(R.id.eventItemTitle)
        val date: TextView = itemView.findViewById(R.id.eventItemDate)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val eventId =
                        items[position].event_id //
                    listener.onItemClick(eventId)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(eventId: UUID)
    }
}