package com.voye.favoriteweathercasts.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationDataItem
import com.voye.favoriteweathercasts.domain.repository.LocationRepository
import com.voye.favoriteweathercasts.domain.util.Result
import com.voye.favoriteweathercasts.domain.weather.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor (
    private val repository: LocationRepository
) :ViewModel() {

    val locationCityAndCountry = MutableLiveData<String?>()
    val locationLatitude = MutableLiveData<Double?>()
    val locationLongitude = MutableLiveData<Double?>()
    val locationCreated = MutableLiveData<String?>()

    private val _recentLocationsList = MutableLiveData<List<LocationDTO>>()
    val recentLocationsList: LiveData<List<LocationDTO>>
        get() =_recentLocationsList

    /**
     * Clear the live data objects to start fresh next time the view model gets called
     */
    fun onClear(){
        locationCityAndCountry.value = null
        locationLatitude.value = null
        locationLongitude.value = null
        locationCreated.value = null

    }

    /**
     * Save the reminder to the data source
     */
    fun saveLocation(locationData: LocationDataItem){
        viewModelScope.launch {
            repository.saveLocation(
                LocationDTO(
                    locationData.city,
                    locationData.country,
                    locationData.latitude,
                    locationData.longitude,
                    locationData.created,
                    locationData.id
                )
            )
        }
    }

    fun getSearchHistory(){
        viewModelScope.launch {

            val recentLocationsData = repository.getLocations()

            when(recentLocationsData){
                is Result.Success -> {
                    _recentLocationsList.value = recentLocationsData.data
                }
                is Result.Error -> {
                    Log.d("Error", "Could not retrieve data from database")
                }

            }
        }

    }

}


