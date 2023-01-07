package com.voye.favoriteweathercasts

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.volley.VolleyLog
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.voye.favoriteweathercasts.databinding.ActivityMainBinding
import com.voye.favoriteweathercasts.presentation.PlacesAutocompleteActivity
import com.voye.favoriteweathercasts.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var placesClient: PlacesClient


    val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.progressBarIndicator.observe(this) {
            if (viewModel.progressBarIndicator.value == true){
                binding.progressSpinner.visibility = ProgressBar.VISIBLE
                binding.progressBarBackground.visibility = View.VISIBLE

            } else {
                binding.progressSpinner.visibility = ProgressBar.GONE
                binding.progressBarBackground.visibility = View.GONE
            }
        }

        /*viewModel.isNetworkAvailable.observe(this){
            if (viewModel.isNetworkAvailable.value == false){
                binding.noNetworkBackground.visibility = View.GONE
            } else {
                binding.noNetworkBackground.visibility = View.VISIBLE
            }
        }*/

        val bundle = intent.extras

        if (bundle != null){
            val myNewCity = bundle!!.getString("city_and_country_key")
            Log.d("-------IntentNewCity",myNewCity!! )
            viewModel.setCurrentLocationText(myNewCity!!)

            val myNewLatidtude = bundle!!.getDouble("latitude_key")
            val myNewLongitude = bundle!!.getDouble("longitude_key")
            viewModel.loadWeatherInfo(myNewLatidtude, myNewLongitude)
        }

        val placesApiKey = BuildConfig.PLACES_API_KEY

        if (placesApiKey.isEmpty()){
            Toast.makeText(this, "No API defined in gradle properties", Toast.LENGTH_LONG).show()
        }

        // Setup Places Client
        if (!Places.isInitialized()){
            Places.initialize(applicationContext, placesApiKey)
        }
        placesClient = Places.createClient(this)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){

            if (bundle == null){
                viewModel.loadWeatherInfoFromCurrentLocation()
                viewModel.loadLocationName()
            }

        }
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Navigation.findNavController(this, R.id.nav_host_fragment)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView, navController)

        binding.root


    }

}