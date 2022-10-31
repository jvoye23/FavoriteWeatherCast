package com.voye.favoriteweathercasts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.voye.favoriteweathercasts.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() =_binding!!

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