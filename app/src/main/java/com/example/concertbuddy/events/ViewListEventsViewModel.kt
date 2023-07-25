package com.example.concertbuddy.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.concertbuddy.events.calendarData

class ViewListEventsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
}

class ViewListEventsViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    /**
     * This class is responsible for creating the ViewListEventsViewModel, because the default ViewModel constructor
     * does not allow us to pass in any parameters.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewListEventsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewListEventsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}