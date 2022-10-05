package com.voye.favoriteweathercasts.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.R

import com.voye.favoriteweathercasts.databinding.FragmentTodayForecastBinding
import com.voye.favoriteweathercasts.forecasts.TodayForecastAdapter
import com.voye.favoriteweathercasts.presentation.WeatherViewModel

/**
 * This [Fragment] will show the location's weather forecast of today
 */

@RequiresApi(Build.VERSION_CODES.O)
class TodayForecastFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView


    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTodayForecastBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the MainViewModel
        binding.viewModel = viewModel

        //binding.imageView.setImageDrawable(viewModel.weatherIconPath.value)
        Log.d("IMAGE", "weather icon path = ${viewModel.weatherIconPath.value}")
        Log.d("icon string", "icon string = ${viewModel.weatherIconString}")

        //set the adapter to the RecyclerView for hourly forecasts of today
        val adapter = TodayForecastAdapter()

        recyclerView = binding.hourlyForecastList
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        //set the data to the adapter for the RecyclerView to show
        viewModel.weatherDataResponse.observe(viewLifecycleOwner) {
            adapter.saveData(it.hourly)
        }
        return binding.root

    }
}