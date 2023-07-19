package com.example.concertbuddy.FriendsAndGroups

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R

class CreateGroupsFragment : Fragment() {

    companion object {
        fun newInstance() = CreateGroupsFragment()
    }

    private lateinit var viewModel: CreateGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_groups, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateGroupsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}