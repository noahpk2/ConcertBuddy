package com.example.concertbuddy.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.concertbuddy.calendar.CalendarData

import com.example.concertbuddy.events.data.EventRepository
import kotlinx.coroutines.launch

class ViewListEventsViewModel(private val repository: EventRepository) : ViewModel(){
    private val _events: MutableLiveData<MutableList<CalendarData.Event>> = MutableLiveData(mutableListOf())
    val events: LiveData<MutableList<CalendarData.Event>> = _events

    init {
        viewModelScope.launch {
            try {
                val fetchedEvents = repository.getEvents()
                _events.value = fetchedEvents
            }
            catch (e:Exception){
                //Handle error
            }
        }

    }

    /*fun addEvent(event: CalendarData.Event) {
        // Your addEvent logic here
        repository.addEvent(event)
        _events.value = repository.getEvents()
    }*/

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