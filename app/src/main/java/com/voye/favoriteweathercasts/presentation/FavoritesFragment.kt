package com.voye.favoriteweathercasts.presentation

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.voye.favoriteweathercasts.MainActivity
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.local.FavoriteLocationDTO
import com.voye.favoriteweathercasts.databinding.FragmentFavoritesBinding
import com.voye.favoriteweathercasts.utils.startAnimation
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * This [Fragment] will show the recycler view with user's favorite cities.
 */

@AndroidEntryPoint
class FavoritesFragment() : Fragment() {

    private lateinit var recylerView: RecyclerView
    private val viewModel: FavoritePlacesViewModel by activityViewModels()
    private lateinit var favoritePlaceAdapter: FavoritePlacesAdapter
    private lateinit var addFab: ExtendedFloatingActionButton
    private lateinit var manageFavoriteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFavoritesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        //binding.lifecycleOwner = this

        // Initialize members
        recylerView = binding.favoritePlacesRecylerView
        addFab = binding.addFab

        addFab.setOnClickListener {
            binding.addFab.isVisible = false
            val intent = Intent(context, PlacesAutocompleteActivity::class.java)
            startActivity(intent)
            binding.addFab.isVisible = true
        }

        manageFavoriteButton = binding.manageFavoritesButton
        manageFavoriteButton.setOnClickListener {

            if (!favoritePlaceAdapter.isDeleteButtonVisible){
                favoritePlaceAdapter.isDeleteButtonVisible = true
                manageFavoriteButton.text = "Done"
                recylerView.adapter = favoritePlaceAdapter
                addFab.visibility = View.GONE

            } else {
                favoritePlaceAdapter.isDeleteButtonVisible = false
                manageFavoriteButton.text = "Manage"
                recylerView.adapter = favoritePlaceAdapter
                addFab.visibility = View.VISIBLE
            }

        }

        favoritePlaceAdapter = FavoritePlacesAdapter(
            FavoritePlacesAdapter.FavoritePlaceOnClickListener {
                    clickedFavoriteLocation -> showWeatherOfFavoritePlace(clickedFavoriteLocation)
                    },
            FavoritePlacesAdapter.DeleteFavoritePlaceOnClickListener {
                clickedFavoriteLocation -> deleteMyFavoritePlace(clickedFavoriteLocation)

            }
        )

        initFavoritePlacesRecycleverView()

        viewModel.getFavoritePlaces()
        recylerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // Scroll down
                if (dy > 10 && addFab.isExtended){
                    addFab.shrink()
                }
                // Scroll up
                if (dy < -10 && !addFab.isExtended){
                    addFab.extend()
                }
                // At the top
                if (!recyclerView.canScrollVertically(-1)){
                    addFab.extend()
                }
            }
        })

        return binding.root
    }

    private fun deleteMyFavoritePlace(clickedFavoriteLocation: FavoriteLocationDTO) {
        Log.d("---->DeleteFavorite", "Favorite: ${clickedFavoriteLocation.city}")
        viewModel.deleteFavoritePlace(clickedFavoriteLocation.id)
        viewModel.isFavoritePlaceDeleted.observe(viewLifecycleOwner){
            if(viewModel.isFavoritePlaceDeleted.value == true){
                Log.d("---->DeleteFavorite", "FavoriteDeleted: ${clickedFavoriteLocation.city}")
            }
        }
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

    private fun showWeatherOfFavoritePlace(favoriteLocationDTO: FavoriteLocationDTO){
        val latitude = favoriteLocationDTO.latitude
        val longitude = favoriteLocationDTO.longitude
        val city = favoriteLocationDTO.city
        val country = favoriteLocationDTO.country
        val cityAndCountry = "${city}, ${country}"
        val bundle = Bundle()
        bundle.putDouble("latitude_key", latitude!!)
        bundle.putDouble("longitude_key", longitude!!)
        bundle.putString("city_and_country_key", cityAndCountry)
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

// this crashes the application due to Null Pointer Exception
/*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }*/

}