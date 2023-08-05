package com.example.concertbuddy.events


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.concertbuddy.calendar.CalendarData

import com.example.concertbuddy.events.data.EventRepository
import com.example.concertbuddy.events.data.SerpApiClient
import kotlinx.coroutines.launch

import java.util.UUID


class ViewSingleEventViewModel(private val eventRepository: EventRepository, private val eventId: UUID) : ViewModel() {

    private val _event = MutableLiveData<CalendarData.Event>()
    val event: LiveData<CalendarData.Event> get() = _event

    init {
        viewModelScope.launch {
            _event.value = eventRepository.getEvent(eventId)
        }
    }
}





class ViewSingleEventViewModelFactory(
    private val appContext: Context,
    private val eventId: UUID
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewSingleEventViewModel::class.java)) {
            val repository = EventRepository.getInstance(appContext, SerpApiClient.create())
            @Suppress("UNCHECKED_CAST")
            return ViewSingleEventViewModel(repository, eventId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}