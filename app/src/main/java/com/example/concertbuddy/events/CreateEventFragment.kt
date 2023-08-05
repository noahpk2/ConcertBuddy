package com.example.concertbuddy.events

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import com.example.concertbuddy.R
import com.example.concertbuddy.application.ConcertBuddy.Companion.getDatabase
import com.example.concertbuddy.application.DayDao
import com.example.concertbuddy.application.LocalDatabase
import com.example.concertbuddy.calendar.CalendarData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.UUID


/**
 * Fragment for creating an event. This fragment is responsible for displaying the create event form and interacting with the create event view model.
 *
 */
class CreateEventFragment : Fragment() {
    companion object {
        private const val TAG = "CreateEventFragment"
    }
    private lateinit var appContext: Context
    private lateinit var database: LocalDatabase
    private lateinit var dayDao: DayDao


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

        appContext = requireContext().applicationContext
        database = getDatabase(appContext)
        dayDao = database.dayDao()

        // Rest of your onViewCreated method code...



        fun eventTimeTo24Hour(eventTime: String, eventTimeAorP: String): String {
            if (eventTimeAorP == "PM") {
                var hour = eventTime.split(":")[0].toInt() + 12
                var minutes = eventTime.split(":")[1].toInt()
                var eventTime = "$hour:$minutes"

            }
            return eventTime
        }

        val createEventButton = view.findViewById<View>(R.id.create_event_button)

        /**
         * Listener for the create event button. This listener is responsible for retrieving the values from the EditText fields and creating an event object.
         *
         */
        createEventButton.setOnClickListener {
            // Retrieve the values from the EditText fields
            var eventTitle = view.findViewById<EditText>(R.id.event_name_edit_text).toString()
            var eventDescription= view.findViewById<EditText>(R.id.event_description_edit_text).toString()
            var datePicker = view.findViewById<DatePicker>(R.id.event_date_picker)
            var eventDate = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            var eventLocation = view.findViewById<EditText>(R.id.event_location_edit_text).toString()
            var eventTime = view.findViewById<EditText>(R.id.event_time_edit_text).toString()
            var eventTimeAMorPM = view.findViewById<RadioGroup>(R.id.radioGroupAMPM).toString()

            // Convert the eventTime to 24 hour time
            eventTime = eventTimeTo24Hour(eventTime, eventTimeAMorPM)

            // TODO: Create an Event object using the retrieved values
            CoroutineScope(Dispatchers.IO).launch {
                val newEvent = CalendarData.Event(
                    event_id = UUID.randomUUID(),
                    day_id = (dayDao.getDayByDate(eventDate) ?: UUID.randomUUID()) as UUID,
                    title = eventTitle,
                    eventTime,
                    eventLocation,
                    eventDescription,
                    eventDate
                )
            }


        }
    }

    // TODO : create listener for button to create event

    // TODO: send event to "database" (arraylist)
    fun getDayID(eventDate: String): UUID {
        return UUID.randomUUID() //TODO: replace with actual day id
    }
}


