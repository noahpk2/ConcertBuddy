package com.example.concertbuddy.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * This is the ViewModel for the calendar. it provides an interface between the UI and the data layer.
 */
class CalendarViewModel (private val repository: CalendarRepository) : ViewModel() {
    private val _calendarItems: MutableLiveData<MutableList<CalendarItem>> = MutableLiveData(mutableListOf())
    val calendarItems: LiveData<MutableList<CalendarItem>> = _calendarItems

    init {
        viewModelScope.launch {
            try {
                val fetchedEvents = repository.getCalendarItems()
                _calendarItems.value = fetchedEvents
            }
            catch (e:Exception){
                //Handle error
            }
        }
    }

    suspend fun addEvent(event: CalendarData.Event) {
        // Your addEvent logic here
        repository.addEvent(event)
        _calendarItems.value = repository.getCalendarItems()
    }
}

class CalendarViewModelFactory(private val repository: CalendarRepository) : ViewModelProvider.Factory {
    /**
     * This class is responsible for creating the CalendarViewModel, because the default ViewModel constructor
     * does not allow us to pass in any parameters.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}