package com.voye.favoriteweathercasts.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voye.favoriteweathercasts.data.local.FavoriteLocationDTO
import com.voye.favoriteweathercasts.data.local.FavoriteLocationDataItem
import com.voye.favoriteweathercasts.domain.repository.FavoriteLocationRepository
import com.voye.favoriteweathercasts.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePlacesViewModel @Inject constructor(
    private val repository: FavoriteLocationRepository
): ViewModel() {

    private val _favoritePlacesList = MutableLiveData<List<FavoriteLocationDTO>>()
    val favoritePlacesList: LiveData<List<FavoriteLocationDTO>>
        get() = _favoritePlacesList


    /**
     * Save the favorite place to the data source
     */
    fun saveFavoritePlace(favoriteLocation: FavoriteLocationDataItem){
        viewModelScope.launch {
            repository.saveFavoriteLocation(
                FavoriteLocationDTO(
                    favoriteLocation.city,
                    favoriteLocation.country,
                    favoriteLocation.latitude,
                    favoriteLocation.longitude,
                    favoriteLocation.created,
                    favoriteLocation.id
                )
            )
        }
    }

    fun getFavoritePlaces(){
        viewModelScope.launch {
            val favoritePlacesData = repository.getFavoriteLocations()

            when(favoritePlacesData){
                is Result.Success ->{
                    _favoritePlacesList.value = favoritePlacesData.data
                }
                is Result.Error -> {
                    Log.d("Error", "Could not retrieve favorite places form database")
                }
            }
        }
    }


}