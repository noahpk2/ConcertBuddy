package com.example.concertbuddy.events

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R

class ViewSingleEventFragment : Fragment() {

    companion object {
        fun newInstance() = ViewSingleEventFragment()
    }

    private lateinit var viewModel: ViewSingleEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_single_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewSingleEventViewModel::class.java)
        // TODO: Use the ViewModel
    }

}