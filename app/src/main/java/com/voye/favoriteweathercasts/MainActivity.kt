package com.voye.favoriteweathercasts

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.voye.favoriteweathercasts.databinding.ActivityMainBinding
import com.voye.favoriteweathercasts.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.jar.Manifest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigation: Navigation

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val mySearchButton = binding.searchButton
        mySearchButton.setOnClickListener {
            onSearchRequested()
            //binding.searchTextView.text = "Hello World"

        }
        /*binding.searchButton.setOnClickListener {
            onSearchRequested()
        }*/

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

}