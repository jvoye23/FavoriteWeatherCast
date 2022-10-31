package com.voye.favoriteweathercasts.presentation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This [Fragment] will show the recycler view with user's favorite cities.
 */

@AndroidEntryPoint
class FavoritesFragment() : Fragment() {

    private lateinit var recylerView: RecyclerView
    private val viewModel: FavoritePlacesViewModel by activityViewModels()
    private lateinit var favoritePlaceAdapter: FavoritePlacesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFavoritesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        //binding.viewModel = viewModel

        // Initialize members
        recylerView = binding.favoritePlacesRecylerView
        favoritePlaceAdapter = FavoritePlacesAdapter(FavoritePlacesAdapter.FavoritePlaceOnClickListener{
            clickedFavoriteLocation ->
            Toast.makeText(context, "${clickedFavoriteLocation.city}", Toast.LENGTH_LONG).show()
        })

        initFavoritePlacesRecycleverView()



        return binding.root
    }

    private fun initFavoritePlacesRecycleverView(){
        val layoutManager = LinearLayoutManager(context)
        recylerView.layoutManager = layoutManager
        recylerView.adapter = favoritePlaceAdapter
        recylerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        viewModel.favoritePlacesList.observe(viewLifecycleOwner){
            favoritePlaceAdapter.saveData(it)
        }
    }


}