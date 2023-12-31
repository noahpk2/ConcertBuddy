package com.example.concertbuddy.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.concertbuddy.R
import com.example.concertbuddy.application.ConcertBuddy
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController



/**
 * Fragment for registering a user. This fragment is responsible for displaying the register form and interacting with the register view model.
 * TODO: Implement this fragment and its view model.
 */
class UserRegisterFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonRegister: Button

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
        // Assume you have a EditText and a Button in your fragment_register_user.xml
        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextLocation = view.findViewById(R.id.editTextLocation)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        buttonRegister = view.findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            registerUser()
        }


    }

    private fun registerUser() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        val location = editTextLocation.text.toString()
        val email = editTextEmail.text.toString()



        // You might want to validate the username and password here before saving them

        // Save user entered fields to SharedPreferences
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("location", location)
        editor.putString("email", email)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()

        // Navigate to ProfileFragment
        findNavController().navigate(R.id.action_register_to_profile)
    }
}

