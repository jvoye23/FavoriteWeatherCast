package com.voye.favoriteweathercasts

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
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
    private lateinit var navigation: Navigation
    private lateinit var placesClient: PlacesClient

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            //viewModel.loadWeatherInfo(viewModel.myCurrentLatitude.value!!.toDouble(), viewModel.myCurrentLatitude.value!!.toDouble())
            viewModel.loadWeatherInfo(53.6132490977546, 10.014867811927456)
            viewModel.loadLocationName()
        }
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set listeners for Autocomplete activity
        val mySearchTextView = binding.searchTextView
        mySearchTextView.setOnClickListener { startAutocompleteActivity() }

        val mySearchButton = binding.searchButton
        mySearchButton.setOnClickListener {
            //onSearchRequested()
            val intent = Intent(this, PlacesAutocompleteActivity::class.java)
            startActivity(intent)
        }


        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        Navigation.findNavController(this, R.id.nav_host_fragment)


        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        /*val searchBox = findViewById<SearchView>(R.id.searchView)

        searchBox.queryHint = resources.getString(R.string.search_location)
        searchBox.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchBox.clearFocus()
                searchBox.setQuery("", false)

                Toast.makeText(this@MainActivity, "Looking for $query", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })*/
    }

    private fun startAutocompleteActivity(){
        val placeFields = listOf<com.google.android.libraries.places.api.model.Place.Field>(
            com.google.android.libraries.places.api.model.Place.Field.ID,
            com.google.android.libraries.places.api.model.Place.Field.LAT_LNG,
            com.google.android.libraries.places.api.model.Place.Field.NAME)
        val myTypeFilter: TypeFilter = TypeFilter.CITIES

        val autocompleteIntent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFields)
            .setHint("Search places")
            .setTypeFilter(myTypeFilter)
            .build(this)
        startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && data != null) {
            when (resultCode) {
                AutocompleteActivity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        val placeLat = place.latLng.latitude
                        val placeLon = place.latLng.longitude
                        Log.i(VolleyLog.TAG, "My Place: ${place.name}, ${place.id}, ${place.latLng}")

                        viewModel.setCurrentLocationText(place.name)
                        viewModel.loadWeatherInfo(placeLat, placeLon)

                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(VolleyLog.TAG, status.statusMessage ?: "")
                        //responseView.text = status.statusMessage
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
    }

}