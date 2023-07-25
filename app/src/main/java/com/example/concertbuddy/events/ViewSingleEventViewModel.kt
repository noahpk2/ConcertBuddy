package com.example.concertbuddy.events

import androidx.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.concertbuddy.events.calendarData



class ViewSingleEventViewModel (private val repository: EventRepository) : ViewModel() {
    private var _events: MutableList<calendarData.Event> = MutableLiveData(mutableListOf())
    val events: LiveData<MuteableList<Events>>= _events
    // TODO: Implement the ViewModel
}