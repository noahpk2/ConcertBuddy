package com.example.concertbuddy.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.concertbuddy.R
import com.example.concertbuddy.application.ConcertBuddy


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get SharedPreferences
        val sharedPref = ConcertBuddy.getPreferences(requireContext())

        // Read data from SharedPreferences. Note: replace "username", "email", and "displayName" with the keys you used to save the data.
        val username = sharedPref.getString("username", "default value")
        val email = sharedPref.getString("email", "default value")
        val location = sharedPref.getString("location", "default value")

        var profileName:TextView = view.findViewById(R.id.profile_name)
        var profileEmail:TextView = view.findViewById(R.id.profile_email)
        var profileLocation:TextView = view.findViewById(R.id.profile_location)

        // Update UI with the read data. Note: replace "usernameText", "emailText", and "displayNameText" with the actual IDs of your TextViews in the fragment_profile_view layout.
        profileName.text = username
        profileEmail.text = email
        profileLocation.text = location
    }
}