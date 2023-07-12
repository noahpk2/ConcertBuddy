package com.example.concertbuddy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.concertbuddy.R
import com.google.android.material.navigation.NavigationView


class ProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProfileActivity"
    }

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.my_drawer_layout)
        val navView: NavigationView =
            findViewById(R.id.navView) // Replace with the id of your NavigationView

        // Set item selected listener
        navView.setNavigationItemSelectedListener { menuItem ->
            Log.d(TAG, "Menu Item Selected: ${menuItem.itemId}")
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    Log.d(TAG, "Logout selected")
                    // TODO(Perform logout operation here)
                    true
                }

                else -> {
                    // This will allow the other menu item selections to navigate properly
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawers() // Close navigation drawer
                    true
                }
            }
        }

        // decide where to navigate based on login status
        val isLoggedIn = checkUserLoginStatus()
        if (isLoggedIn) {
            // Navigate to profile view
            navController.navigate(R.id.profileViewFragment)
        } else {
            // Navigate to login screen

            navController.navigate(R.id.loginFragment)
        }
    }

    private fun checkUserLoginStatus(): Boolean {
        // Check if user is logged in
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allow NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return navController.navigateUp(AppBarConfiguration(navController.graph, drawerLayout))
                || super.onSupportNavigateUp()
    }
}
