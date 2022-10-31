package com.voye.favoriteweathercasts.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.GsonBuilder
import com.voye.favoriteweathercasts.BuildConfig
import com.voye.favoriteweathercasts.MainActivity
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationDataItem
import com.voye.favoriteweathercasts.utils.GeocodingResult
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import java.time.LocalDateTime

@AndroidEntryPoint
class PlacesAutocompleteActivity: AppCompatActivity() {

    private val viewModel: SearchHistoryViewModel by viewModels()
    private val handler = Handler()
    private val placePredictionAdapter = PlacePredictionAdapter()

    private val gson = GsonBuilder().registerTypeAdapter(LatLng::class.java, LatLngAdapter()).create()

    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var queue: RequestQueue
    private lateinit var placesClient: PlacesClient
    private var sessionToken: AutocompleteSessionToken? = null

    private lateinit var viewAnimator: ViewAnimator
    private lateinit var progressBar: ProgressBar
    private lateinit var myAutoCompleteSearchView: androidx.appcompat.widget.SearchView
    private lateinit var toolbar: Toolbar
    private lateinit var recentSearchTextView: TextView
    private lateinit var searchHistoryRecyclerView: RecyclerView
    //private lateinit var saveToFavoritesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_places_autocomplete)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Initialize members
        progressBar = findViewById(R.id.progress_bar)
        viewAnimator = findViewById(R.id.view_animator)
        myAutoCompleteSearchView = findViewById(R.id.my_autocomplete_search_view)
        placesClient = Places.createClient(this)
        queue = Volley.newRequestQueue(this)
        toolbar = findViewById(R.id.toolbar)
        recentSearchTextView = findViewById(R.id.recent_searches_textView)
        searchHistoryRecyclerView = findViewById(R.id.recent_searches_recycler_view)
        searchHistoryAdapter = SearchHistoryAdapter(SearchHistoryAdapter.SearchHistoryOnClickListener{
            clickedLocation ->
            //Toast.makeText(applicationContext, "${clickedLocation.city}", Toast.LENGTH_LONG).show()
            showWeatherWithRecentSearch(clickedLocation)
        })
        //saveToFavoritesButton = findViewById(R.id.save_to_favorites_button)



        toolbar.setNavigationOnClickListener { v ->
            finish()
        }

        initMyAutocompleteSearchView(myAutoCompleteSearchView)
        viewModel.getSearchHistory()
        initSearchHistoryRecylerView()

        initRecyclerView()
    }



    // this event will enable the back
    // function to the button on press
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }


    private fun initMyAutocompleteSearchView(searchView: androidx.appcompat.widget.SearchView){
        searchView.queryHint = "Search city"
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        sessionToken = AutocompleteSessionToken.newInstance()

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                progressBar.isIndeterminate = true

                if (newText.isEmpty()){
                    recentSearchTextView.visibility = View.VISIBLE
                    searchHistoryRecyclerView.visibility = View.VISIBLE
                } else{
                    recentSearchTextView.visibility = View.GONE
                    searchHistoryRecyclerView.visibility = View.GONE
                }

                // Cancel any previous place prediction requests
                handler.removeCallbacksAndMessages(null)

                // Start a new place prediction request in 300 ms
                handler.postDelayed({ getPlacePredictions(newText) }, 300)
                return true
            }

        })
    }

    private fun initSearchHistoryRecylerView(){
        val layoutManager = LinearLayoutManager(this)
        searchHistoryRecyclerView.layoutManager = layoutManager
        searchHistoryRecyclerView.adapter = searchHistoryAdapter
        searchHistoryRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        viewModel.recentLocationsList.observe(this){
            searchHistoryAdapter.saveData(it)
        }

        //placePredictionAdapter.onPlaceClickListener = { geocodePlaceAndDisplay(it) }
    }



    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.places_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = placePredictionAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        placePredictionAdapter.onPlaceClickListener = { geocodePlaceAndDisplay(it) }
    }

    private fun getPlacePredictions(query: String) {
        // The value of 'bias' biases prediction results to the rectangular region provided
        // (currently Kolkata). Modify these values to get results for another area. Make sure to
        // pass in the appropriate value/s for .setCountries() in the
        // FindAutocompletePredictionsRequest.Builder object as well.

        // Create a new programmatic Place Autocomplete request in Places SDK for Android
        val newRequest = FindAutocompletePredictionsRequest
            .builder()
            .setSessionToken(sessionToken)
            .setTypeFilter(TypeFilter.CITIES)
            .setQuery(query)
            .build()

        // Perform autocomplete predictions request
        placesClient.findAutocompletePredictions(newRequest).addOnSuccessListener { response ->
            val predictions = response.autocompletePredictions
            placePredictionAdapter.setPredictions(predictions)
            progressBar.isIndeterminate = false
            viewAnimator.displayedChild = if (predictions.isEmpty()) 0 else 1
        }.addOnFailureListener { exception: Exception? ->
            progressBar.isIndeterminate = false
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: " + exception.statusCode)
            }
        }
    }

    /**
     * Performs a Geocoding API request and displays the result in a dialog.
     *
     * @see https://developers.google.com/places/android-sdk/autocomplete#get_place_predictions_programmatically
     */
    private fun geocodePlaceAndDisplay(placePrediction: AutocompletePrediction) {
        // Construct the request URL
        val apiKey = BuildConfig.PLACES_API_KEY
        val requestURL =
            "https://maps.googleapis.com/maps/api/geocode/json?place_id=${placePrediction.placeId}&key=$apiKey"

        // Use the HTTP request URL for Geocoding API to get geographic coordinates for the place
        val request = JsonObjectRequest(Request.Method.GET, requestURL, null, { response ->
            try {
                // Inspect the value of "results" and make sure it's not empty
                val results: JSONArray = response.getJSONArray("results")
                if (results.length() == 0) {
                    Log.w(TAG, "No results from geocoding request.")
                    return@JsonObjectRequest
                }

                // Use Gson to convert the response JSON object to a POJO
                val result: GeocodingResult = gson.fromJson(results.getString(0), GeocodingResult::class.java)
                //displayDialog(placePrediction, result)
                showWeather(placePrediction, result)
                //saveToFavoritesButton.setOnClickListener {viewModel.saveFavoritePlace(placePrediction, result)}
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error ->
            Log.e(TAG, "Request failed", error)
        })

        // Add the request to the Request queue.
        queue.add(request)
    }

    private fun displayDialog(place: AutocompletePrediction, result: GeocodingResult): Unit {
        AlertDialog.Builder(this)
            .setTitle(place.getPrimaryText(null))
            .setMessage("Geocoding result:\n" + result.geometry?.location + "\n"+ "Addresss: "+ place.getFullText(null))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun showWeatherWithRecentSearch(locationDTO: LocationDTO){
        var latitude = locationDTO.latitude
        var longitude = locationDTO.longitude
        var city = locationDTO.city
        var country = locationDTO.country
        var cityAndCountry = "${city}, ${country}"
        val bundle = Bundle()
        bundle.putDouble("latitude_key", latitude!!)
        bundle.putDouble("longitude_key", longitude!!)
        bundle.putString("city_and_country_key", cityAndCountry)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)

    }


    private fun showWeather(place: AutocompletePrediction, result: GeocodingResult){
        var latitude = result.geometry?.location?.latitude
        var longitude = result.geometry?.location?.longitude
        var city = place.getPrimaryText(null).toString()
        var country = place.getSecondaryText(null).toString()
        var cityAndCountry = place.getFullText(null).toString()
        val bundle = Bundle()
        bundle.putDouble("latitude_key", latitude!!)
        bundle.putDouble("longitude_key", longitude!!)
        bundle.putString("city_and_country_key", cityAndCountry)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtras(bundle)

        // Save Search Result into Room Database
        val locationItem = LocationDataItem(
            city = city,
            country = country,
            latitude = latitude,
            longitude = longitude,
            created = LocalDateTime.now().toString()
        )
        viewModel.saveLocation(locationItem)

        startActivity(intent)

    }



    companion object {
        private val TAG = PlacesAutocompleteActivity::class.java.simpleName
    }
}