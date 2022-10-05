package com.voye.favoriteweathercasts.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalArgumentException

class ViewPagerAdapter(fragmentActivity: ForecastFragment) :
    FragmentStateAdapter(fragmentActivity) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                TodayForecastFragment()

            }
            1 -> {
                TomorrowForecastFragment()

            }
            2 -> {
                EightDayForecastFragment()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 3
    }
}