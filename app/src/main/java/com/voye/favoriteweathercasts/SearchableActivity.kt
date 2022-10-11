package com.voye.favoriteweathercasts


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SearchRecentSuggestionsProvider
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.databinding.SearchBinding
import com.voye.favoriteweathercasts.presentation.SearchResultAdapter
import com.voye.favoriteweathercasts.presentation.WeatherViewModel
import com.voye.favoriteweathercasts.provider.MySuggestionProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        viewModel.geocoding.observe(this) {
            adapter.saveData(it)
        }


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                    .saveRecentQuery(query, null)

                doMySearch(query)
            }
        }
    }

    //Clearing the Suggestion Data
    private fun clearSuggestionData(){
        SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .clearHistory()
    }

    private fun doMySearch(query: String) {

        viewModel.loadCoordinates(query)

    }
}

