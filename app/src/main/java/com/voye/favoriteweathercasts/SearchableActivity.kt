package com.voye.favoriteweathercasts

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.databinding.SearchBinding
import com.voye.favoriteweathercasts.presentation.SearchResultAdapter
import com.voye.favoriteweathercasts.presentation.WeatherViewModel



class SearchableActivity: AppCompatActivity() {

    val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: SearchBinding
    private lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent(intent)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = SearchResultAdapter()
        recyclerView = binding.searchResultList
        recyclerView.adapter = adapter

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }
    }

    private fun doMySearch(query: String) {

        viewModel.loadCoordinates(query)
        viewModel.geocoding.observe(this){
            SearchResultAdapter().saveData(it)
        }

    }



}