package com.voye.favoriteweathercasts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.databinding.FragmentEightdayForecastBinding

import com.voye.favoriteweathercasts.presentation.EightDayForecastAdapter
import com.voye.favoriteweathercasts.presentation.WeatherViewModel

/**
 * This [Fragment] will show the location's weather forecast of today
 */

class EightDayForecastFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: WeatherViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentEightdayForecastBinding.inflate(inflater)
        val sevenDayForecastAdapter = EightDayForecastAdapter()

        recyclerView = binding.sevenDayForecastList
        val layoutManager= LinearLayoutManager(context)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        recyclerView.adapter = sevenDayForecastAdapter

        viewModel.weatherDataResponse.observe(viewLifecycleOwner){
            sevenDayForecastAdapter.saveData(it.daily)
        }



        return binding.root

    }
}
