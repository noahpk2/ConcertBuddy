package com.example.concertbuddy.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R

/**
 * Fragment for viewing a user's profile. This fragment is responsible for displaying the user's profile information and interacting with the view model.
 * TODO: Implement this fragment and its view model.
 */
class ProfileViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_view, container, false)
    }
}