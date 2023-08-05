package com.example.concertbuddy.events

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.concertbuddy.R
import com.example.concertbuddy.events.data.EventRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.UUID

class ViewSingleEventFragment : Fragment(), OnMapReadyCallback {
    private val args: ViewSingleEventFragmentArgs by navArgs()
    private val viewModel: ViewSingleEventViewModel by viewModels {
        ViewSingleEventViewModelFactory(requireContext(), UUID.fromString(args.eventId))
    }
    private var googleMap: GoogleMap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_single_event, container, false)
        val eventTitleTextView: TextView = view.findViewById(R.id.event_title)
        val eventDateTextView: TextView = view.findViewById(R.id.event_date)
        val eventTimeTextView: TextView = view.findViewById(R.id.event_time)
        val eventLocationTextView: TextView = view.findViewById(R.id.event_location)
        val eventDescriptionTextView: TextView = view.findViewById(R.id.event_description)


        viewModel.event.observe(viewLifecycleOwner) { event ->
            eventTitleTextView.text = event.title
            eventDateTextView.text = event.date
            eventTimeTextView.text = event.time
            eventLocationTextView.text = event.location
            eventDescriptionTextView.text = event.description

        }
        // Obtain a reference to the SupportMapFragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Get the event location from the ViewModel
        viewModel.event.observe(viewLifecycleOwner) { event ->
            val eventAddress = event.location

            // Geocode the address to obtain a list of possible addresses
            val geocoder = Geocoder(requireContext())

            try {
                val addressList = geocoder.getFromLocationName(eventAddress, 1)
                if (addressList != null && addressList.isNotEmpty()) {
                    val eventLocation = LatLng(addressList[0].latitude, addressList[0].longitude)

                    // Add a marker to the map
                    googleMap?.addMarker(MarkerOptions().position(eventLocation).title(eventAddress))

                    // Move the camera to the event location
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15f))
                } else {
                    // Handle the case where the address was not found
                    // For example, display a toast or a default location marker
                }
            } catch (e: Exception) {
                // Handle any potential exceptions from the Geocoder
                e.printStackTrace()
            }
        }
    }
}