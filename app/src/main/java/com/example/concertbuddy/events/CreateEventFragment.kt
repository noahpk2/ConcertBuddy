package com.example.concertbuddy.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import com.example.concertbuddy.R


class CreateEventFragment : Fragment() {
    companion object {
        private const val TAG = "CreateEventFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        fun eventTimeTo24Hour(eventTime: String, eventTimeAorP: String): String {
            if (eventTimeAorP == "PM") {
                var hour = eventTime.split(":")[0].toInt() + 12
                var minutes = eventTime.split(":")[1].toInt()
                var eventTime = "$hour:$minutes"

            }
            return eventTime
        }

        val createEventButton = view.findViewById<View>(R.id.create_event_button)

        createEventButton.setOnClickListener {
            // Retrieve the values from the EditText fields
            var eventTitle = view.findViewById<EditText>(R.id.event_name_edit_text).toString()
            var eventDescription= view.findViewById<EditText>(R.id.event_description_edit_text).toString()
            var eventDate = view.findViewById<DatePicker>(R.id.event_date_picker).toString()
            var eventLocation = view.findViewById<EditText>(R.id.event_location_edit_text).toString()
            var eventTime = view.findViewById<EditText>(R.id.event_time_edit_text).toString()
            var eventTimeAMorPM = view.findViewById<RadioGroup>(R.id.radioGroupAMPM).toString()

            // Convert the eventTime to 24 hour time
            eventTime = eventTimeTo24Hour(eventTime, eventTimeAMorPM)

            // Create an Event object using the retrieved values
            val newEvent = Event(eventTitle, eventTime,eventLocation, eventDescription, eventDate )

            // Use the created event object as needed
            // For example, you can pass it to another function or store it in a database

            // Clear the EditText fields if needed

        }
    }

    // TODO : create listener for button to create event

    // TODO: send event to "database" (arraylist)
}


