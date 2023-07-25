package com.example.concertbuddy.events

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concertbuddy.calendar.CalendarAdapter
import com.example.concertbuddy.events.data.EventRepository
import com.example.concertbuddy.events.data.SerpApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Fragment for the calendar. This fragment is responsible for displaying the Events and interacting with the single event view model.
 *
 */


class ViewListEventsFragment : Fragment() {

    companion object {
        fun newInstance() = ViewListEventsFragment()
        val retrofit = Retrofit.Builder()
            .baseUrl("YOUR_BASE_URL_HERE")  // The base URL of your API
            .addConverterFactory(GsonConverterFactory.create()) // If you're using Gson
            .build()

        val apiService: SerpApiService = retrofit.create(SerpApiService::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ViewListEventsViewModel by viewModels{
        ViewListEventsViewModelFactory(repository = EventRepository(appContext = requireContext(), apiService))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_view_list_events, container, false)
        recyclerView = rootView.findViewById(R.id.list_events)

        // Initialize adapter
        var calendarAdapter = CalendarAdapter(emptyList())

        // Observe calendarItems in the view model
        viewModel.calendarItems.observe(viewLifecycleOwner) { calendarItems ->
            // Update the adapter when calendarItems change
            calendarItems?.let {
                calendarAdapter?.updateItems(it)
            } ?: run {
                calendarAdapter?.updateItems(emptyList())
            }
        }



}