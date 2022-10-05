
package com.voye.favoriteweathercasts.presentation
/*
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voye.favoriteweathercasts.data.responses.ApiResource
import com.voye.favoriteweathercasts.domain.location.LocationTracker
import com.voye.favoriteweathercasts.domain.repository.WeatherRepository
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class WeatherBaseViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val application: Application
): ViewModel() {

    */
/**
     * Call loadWeatherInfo() on init so the data will display immediately.
     *//*

    init { GlobalScope.launch {
        loadWeatherInfo()
        }
    }

    suspend fun getMyLocation():  String{
        val location = locationTracker.getCurrentLocation()
        var myLocation: String = "My location is lon: ${location!!.longitude}, lan: ${location!!.latitude}"
        return myLocation
    }

    fun loadWeatherInfo() {
        viewModelScope.launch {

            val location = locationTracker.getCurrentLocation()
            val myWeatherDataResponse =
                location?.let { repository.getWeatherData(it.latitude, it.longitude) }

            Log.d("---->", myWeatherDataResponse.toString())
            when(myWeatherDataResponse){
                is ApiResource.Success -> {



                    //_weatherDataResponse.postValue(myWeatherDataResponse.value!!)
                    _weatherDataResponse.value = myWeatherDataResponse.value!!
                    _myLocation.value = "My location is latitude: ${location!!.latitude}, longitude: ${location!!.longitude}"

                }

                is ApiResource.Error -> {
                    _errorResponse.postValue(myWeatherDataResponse.errorBody.toString())
                }
            }

            _myLocation.value = "My location is latitude: ${location!!.latitude}, longitude: ${location!!.longitude}"
            _currentTemperature.value = weatherDataResponse.value?.current?.temp?.let { round(it).toInt() }
            _currentFeelsLike.value = weatherDataResponse.value?.current?.feels_like?.let { round(it).toInt() }

            weatherDataResponse?.value?.daily?.forEachIndexed { index, daily ->
                dailyMaxTempList.add(index, daily.temp.max)
            }

            weatherDataResponse?.value?.daily?.forEachIndexed { index, daily ->
                dailyMinTempList.add(index, daily.temp.min)
            }

            _todayMaxTemperature.value = dailyMaxTempList[0].toInt()
            _todayMinTemperature.value = dailyMinTempList[0].toInt()

            weatherDataResponse?.value?.current?.weather?.forEachIndexed { index, weather ->
                _weatherDescription.value = weather.description
                _weatherIconString.value = weather.icon
                setWeatherIconPath(_weatherIconString, Contexts.getApplication(application))

            }

        }


    }


}*/
