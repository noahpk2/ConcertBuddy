package com.example.concertbuddy.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalendarViewModel (private val repository: CalendarRepository) : ViewModel() {
    private val _calendarItems: MutableLiveData<MutableList<CalendarItem>> = MutableLiveData(mutableListOf())
    val calendarItems: LiveData<MutableList<CalendarItem>> = _calendarItems

    init {
        // You may want to move getCalendarItems() logic here
        _calendarItems.value = repository.getCalendarItems()
    }

    fun addEvent(event: Event) {
        // Your addEvent logic here
        repository.addEvent(event)
        _calendarItems.value = repository.getCalendarItems()
    }
}

class CalendarViewModelFactory(private val repository: CalendarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}