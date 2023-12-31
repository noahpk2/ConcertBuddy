package com.example.concertbuddy.events

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concertbuddy.calendar.CalendarAdapter
import com.example.concertbuddy.calendar.CalendarData
import com.example.concertbuddy.events.data.EventRepository
import com.example.concertbuddy.events.data.SerpApiClient
import com.example.concertbuddy.events.data.SerpApiService
import retrofit2.Retrofit
import java.util.UUID


/**
 * Fragment for the calendar. This fragment is responsible for displaying the Events and interacting with the single event view model.
 *
 */


class ViewListEventsFragment : Fragment(), ListEventsAdapter.OnItemClickListener {

    companion object {

    }
    private lateinit var recyclerView: RecyclerView
    private var listEventsAdapter: ListEventsAdapter? = null
    private val viewModel: ViewListEventsViewModel by viewModels {
        ViewListEventsViewModelFactory(
            repository = EventRepository.getInstance(
                appContext = requireContext(),
                _SerpApiService = SerpApiClient.create()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_view_list_events, container, false)
        recyclerView = rootView.findViewById(R.id.list_events)

        // Initialize adapter
        listEventsAdapter = ListEventsAdapter(this, emptyList())

        // Observe calendarItems in the view model
        viewModel.events.observe(viewLifecycleOwner) { events ->
            // Update the adapter when events change
            events?.let {
                listEventsAdapter?.updateItems(it)
            } ?: run {
                listEventsAdapter?.updateItems(emptyList())
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = listEventsAdapter




        return rootView
    }

    override fun onItemClick(eventId: UUID) {
        // Here you'll receive the clicked event ID. Now, you can navigate to the
        // ViewSingleEventFragment passing this event ID as an argument.
        val action = ViewListEventsFragmentDirections.actionViewListEventsFragmentToViewSingleEventFragment(eventId.toString())
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = NavHostFragment.findNavController(this)
        val createEventButton = view.findViewById<View>(R.id.create_event_button)
        createEventButton.setOnClickListener {
            navController.navigate(R.id.createEventFragment)

        }
    }
}