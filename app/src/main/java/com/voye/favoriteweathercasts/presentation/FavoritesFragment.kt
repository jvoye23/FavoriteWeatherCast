package com.voye.favoriteweathercasts.presentation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.voye.favoriteweathercasts.databinding.FragmentFavoritesBinding

/**
 * This [Fragment] will show the recycler view with user's favorite cities.
 */

class FavoritesFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentFavoritesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        //binding.viewModel = viewModel
        return binding.root
    }


}