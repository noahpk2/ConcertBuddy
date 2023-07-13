package com.example.concertbuddy.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.concertbuddy.R

/**
 * Fragment for editing a user's profile. This fragment is responsible for displaying the edit profile form and interacting with the edit profile view model.
 * TODO: Implement this fragment and its view model.
 */
class ProfileEditFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_edit, container, false)
    }


}