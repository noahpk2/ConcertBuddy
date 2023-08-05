package com.example.concertbuddy.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R
import com.example.concertbuddy.application.ConcertBuddy
import android.content.SharedPreferences
import androidx.navigation.fragment.findNavController



/**
 * Fragment for registering a user. This fragment is responsible for displaying the register form and interacting with the register view model.
 * TODO: Implement this fragment and its view model.
 */
class UserRegisterFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            sharedPref = ConcertBuddy.getPreferences(it)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ConcertBuddy.isLoggedIn(requireContext())) {
            // Assume you have an action defined in your nav_graph.xml from UserRegisterFragment to ProfileFragment
            findNavController().navigate(R.id.action_register_to_profile)
        }
    }
}
