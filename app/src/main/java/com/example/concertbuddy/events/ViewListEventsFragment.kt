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

/**
 * Fragment for the calendar. This fragment is responsible for displaying the Events and interacting with the single event view model.
 *
 */


class ViewListEventsFragment : Fragment() {

    companion object {
        fun newInstance() = ViewListEventsFragment()
    }
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ViewListEventsViewModel by viewModels{
        ViewListEventsViewModelFactory(repository = EventRepository(appContext = requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_list_events, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewListEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}