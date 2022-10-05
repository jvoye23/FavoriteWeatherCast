package com.voye.favoriteweathercasts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.databinding.FragmentTomorrowForecastBinding
import com.voye.favoriteweathercasts.domain.weather.Hourly
import com.voye.favoriteweathercasts.domain.weather.HourlyWeatherInfo
import com.voye.favoriteweathercasts.presentation.HourlyForecastAdapter
import com.voye.favoriteweathercasts.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * This [Fragment] will show the location's weather forecast of today
 */

@AndroidEntryPoint
class TomorrowForecastFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    //private val viewModel: WeatherViewModel by viewModels()
    private val viewModel: WeatherViewModel by activityViewModels()

    /**
     * Lazily initialize our [WeatherViewModel].
     */
    //private val viewModel: WeatherViewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTomorrowForecastBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = HourlyForecastAdapter()
        recyclerView = binding.hourlyForecastList
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        viewModel.weatherDataResponse.observe(viewLifecycleOwner) {
            adapter.saveData(it.hourly)


        }



        GlobalScope.launch {

            val myLocation = viewModel.getMyLocation()
            binding.myLocationButton.setOnClickListener {
                binding.textView2.text = myLocation
            }

        }

        return binding.root
    }
}