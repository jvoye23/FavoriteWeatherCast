package com.voye.favoriteweathercasts.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.voye.favoriteweathercasts.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() =_binding!!

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root
        val tabLayout = binding.forecastTabs
        val viewPager = binding.viewPager

        viewPager.setAdapter(createCardAdapter())
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Today"
                    1 -> tab.text = "48 hours"
                    2 -> tab.text = "8 Days"
                }
            }).attach()

        // Set listeners for Autocomplete activity
        val mySearchTextView = binding.searchTextView
        mySearchTextView.setOnClickListener {
            val intent = Intent(activity, PlacesAutocompleteActivity::class.java)
            startActivity(intent)
        }

        val mySearchButton = binding.searchButton
        mySearchButton.setOnClickListener {
            val intent = Intent(activity, PlacesAutocompleteActivity::class.java)
            startActivity(intent)
        }
        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return view
    }

    private fun createCardAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(this)
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null

    }



}