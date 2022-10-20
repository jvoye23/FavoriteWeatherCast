package com.voye.favoriteweathercasts.presentation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.ViewAnimator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog.TAG
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
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.utils.GeocodingResult
import org.json.JSONArray
import org.json.JSONException

class PlacesAutocompleteActivity: AppCompatActivity() {

    private val handler = Handler()
    private val adapter = PlacePredictionAdapter()
    private val gson = GsonBuilder().registerTypeAdapter(LatLng::class.java, LatLngAdapter()).create()

    private lateinit var queue: RequestQueue
    private lateinit var placesClient: PlacesClient
    private var sessionToken: AutocompleteSessionToken? = null

    private lateinit var viewAnimator: ViewAnimator
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_places_autocomplete)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Initialize members
        progressBar = findViewById(R.id.progress_bar)
        viewAnimator = findViewById(R.id.view_animator)
        placesClient = Places.createClient(this)
        queue = Volley.newRequestQueue(this)
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        initSearchView(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            sessionToken = AutocompleteSessionToken.newInstance()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.queryHint = getString(R.string.search_a_place)
        searchView.isIconifiedByDefault = false
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                progressBar.isIndeterminate = true

                // Cancel any previous place prediction requests
                handler.removeCallbacksAndMessages(null)

                // Start a new place prediction request in 300 ms
                handler.postDelayed({ getPlacePredictions(newText) }, 300)
                return true
            }
        })
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.places_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        adapter.onPlaceClickListener = { geocodePlaceAndDisplay(it) }
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
            adapter.setPredictions(predictions)
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
                displayDialog(placePrediction, result)
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


    companion object {
        private val TAG = PlacesAutocompleteActivity::class.java.simpleName
    }
}