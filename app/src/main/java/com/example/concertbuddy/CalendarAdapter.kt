package com.example.concertbuddy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(private val calendarItems: List<CalendarItem>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "CalendarAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.calendar_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        val item = calendarItems[position]
        holder.textView.text = item.date

        if(item.date.isEmpty()){
            holder.textView.visibility = View.INVISIBLE
        }else{
            holder.textView.visibility = View.VISIBLE
        }
        // Add more customization as needed
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ")
        return calendarItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.calendarItemTextView)
    }
}