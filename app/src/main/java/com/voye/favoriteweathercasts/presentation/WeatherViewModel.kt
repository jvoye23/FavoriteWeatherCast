package com.voye.favoriteweathercasts.presentation

import android.Manifest.permission.INTERNET
import android.app.Application
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.responses.ApiResource
import com.voye.favoriteweathercasts.domain.location.CoordinatesByLocationName
import com.voye.favoriteweathercasts.domain.location.LocationName
import com.voye.favoriteweathercasts.domain.location.LocationTracker
import com.voye.favoriteweathercasts.domain.repository.WeatherRepository
import com.voye.favoriteweathercasts.domain.weather.Hourly
import com.voye.favoriteweathercasts.domain.weather.WeatherData
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel

class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    //private val reverseGeocodingRepository: ReverseGeocodingRepository,
    private val locationTracker: LocationTracker,
    private val application: Application
): ViewModel() {




    private val _weatherDataResponse = MutableLiveData<WeatherData>()
    val weatherDataResponse: LiveData<WeatherData> get() =_weatherDataResponse

    private val _hourlyForecastList = MutableLiveData<List<Hourly>>()
    val hourlyForecastList: LiveData<List<Hourly>> get() = _hourlyForecastList


    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String> get() = _errorResponse

    private val _hourlyWeatherData = MutableLiveData<List<Hourly>>()
    val hourlyWeatherData: LiveData<List<Hourly>> get() = _hourlyWeatherData

    private val _myLocation = MutableLiveData<String>()
    val myLocation: LiveData<String> get() = _myLocation

    private val _todayMaxTemperature = MutableLiveData<Int>()
    val todayMaxTemperature: LiveData<Int>
        get() = _todayMaxTemperature

    private val _todayMinTemperature = MutableLiveData<Int>()
    val todayMinTemperature: LiveData<Int>
        get() = _todayMinTemperature

    private val _currentTemperature = MutableLiveData<Int>()
    val currentTemperature: LiveData<Int>
        get() = _currentTemperature

    private val _weatherIconPath = MutableLiveData<Drawable>()
    val weatherIconPath: LiveData<Drawable>
        get() = _weatherIconPath

    private val _currentFeelsLike = MutableLiveData<Int>()
    val currentFeelsLike: LiveData<Int>
        get() = _currentFeelsLike

    private val _weatherDescription = MutableLiveData<String>()
    val weatherDescription: LiveData<String>
        get() = _weatherDescription

    private val _weatherIconString = MutableLiveData<String>()
    val weatherIconString: LiveData<String>
        get() = _weatherIconString

    private val _locationNameResponse = MutableLiveData<List<LocationName>?>()
    val locationNameResponse: MutableLiveData<List<LocationName>?>
        get() = _locationNameResponse

    private val _locationCountry = MutableLiveData<String>()
    val locationCountry: LiveData<String>
        get() = _locationCountry

    private val _locationCity = MutableLiveData<String>()
    val locationCity: LiveData<String>
        get() = _locationCity

    private val _currentLocationText = MutableLiveData<String>()
    val currentLocationText: LiveData<String>
        get() = _currentLocationText

    val currentDateTime: String
        @RequiresApi(Build.VERSION_CODES.O)
        get() = getCurrentTime()

    var dailyMaxTempList: MutableList<Double> = mutableListOf()
    var dailyMinTempList: MutableList<Double> = mutableListOf()

    private val _geocoding = MutableLiveData<List<CoordinatesByLocationName>>()
    val geocoding: LiveData<List<CoordinatesByLocationName>>
        get() = _geocoding

    private val _myCurrentLatitude = MutableLiveData<Double>()
    val myCurrentLatitude: LiveData<Double>
        get() = _myCurrentLatitude

    private val _myCurrentLongitude = MutableLiveData<Double>()
    val myCurrentLongitude: LiveData<Double>
        get() = _myCurrentLongitude

    private val _progressBarIndicator = MutableLiveData<Boolean>()
    val progressBarIndicator: LiveData<Boolean>
        get() = _progressBarIndicator

    private val _isNetWorkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean>
        get() = isNetworkAvailable


    fun getMyLocation() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            //var myLocation: String = "My location is lon: ${location!!.longitude}, lan: ${location!!.latitude}"

            if (location != null){
                _myCurrentLatitude.value = location.latitude
                _myCurrentLongitude.value = location.longitude
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(): String {
        val myDateTime: String
        val currentTime = LocalDateTime.now()
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MMMM, HH:mm")
        myDateTime = "${currentTime.format(timeFormatter)} "
        return myDateTime
    }

    fun loadCoordinates(city: String){
        viewModelScope.launch {

            //val city = "Paris"
            val myCoordinatesFromCity = repository.getCoordinatesByLocationName(city)
            Log.d("---->", myCoordinatesFromCity.toString())

            try {
                _geocoding.value = myCoordinatesFromCity
            } catch (ex: Exception){
                Log.d("----Error>", ex.toString())
            }
        }
    }

    fun loadWeatherInfoFromCurrentLocation(){
        _progressBarIndicator.value = true

        viewModelScope.launch {

            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                Log.d("------>Offline_Location", "lat: ${location.latitude.toString()} lon: ${location.longitude.toString()}")
            }

            val myWeatherDataResponse =
                location?.let { repository.getWeatherData(it.latitude, it.longitude) }

            Log.d("---->Current Location", "latitude: ${location?.latitude.toString()}" +
                    "longitude: ${location?.longitude.toString()}" )

            Log.d("---->", myWeatherDataResponse.toString())
            when(myWeatherDataResponse){
                is ApiResource.Success -> {
                    _weatherDataResponse.value = myWeatherDataResponse.value!!
                    if (_weatherDataResponse.value != null){
                        _progressBarIndicator.value = false
                    }

                }
                is ApiResource.Error -> {
                    _errorResponse.postValue(myWeatherDataResponse.errorBody.toString())
                    if (_weatherDataResponse.value == null){
                        _progressBarIndicator.value = true
                    }
                }
            }

            //_myLocation.value = "My location is latitude: ${location!!.latitude}, longitude: ${location!!.longitude}"
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
                setWeatherIconPath(_weatherIconString, getApplication(application))

            }
        }
    }

    fun loadLocationName(){
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            val myLocationNameResponse =
                location?.let { repository.getReverseGeocoding(it.latitude, it.longitude) }

            Log.d("---->", myLocationNameResponse.toString())

            try {
                Log.d("----Success->", myLocationNameResponse.toString())
                _locationCity.value = myLocationNameResponse?.get(0)?.name.toString()
                _locationCountry.value = myLocationNameResponse?.get(0)?.country.toString()
                Log.d("----LocationCity",myLocationNameResponse?.get(0)?.name.toString())
                _currentLocationText.value = myLocationNameResponse?.get(0)?.name.toString() + ", " + myLocationNameResponse?.get(0)?.country.toString()
            } catch (ex: Exception ){
                Log.d("---->", ex.toString())
            }

        }
    }

    fun loadWeatherInfo(latitude: Double, longitude: Double) {
        _progressBarIndicator.value = true

        viewModelScope.launch {

            val location = locationTracker.getCurrentLocation()
            val myWeatherDataResponse =
                 repository.getWeatherData(latitude, longitude)

            Log.d("---->", myWeatherDataResponse.toString())
            when(myWeatherDataResponse){
                is ApiResource.Success -> {

                    //_weatherDataResponse.postValue(myWeatherDataResponse.value!!)
                    _weatherDataResponse.value = myWeatherDataResponse.value!!
                    if (_weatherDataResponse.value != null){
                        _progressBarIndicator.value = false
                    }


                    _myLocation.value = "My location is latitude: ${latitude}, longitude: ${longitude}"
                }
                is ApiResource.Error -> {
                    _errorResponse.postValue(myWeatherDataResponse.errorBody.toString())
                    if (_weatherDataResponse.value == null){
                        _progressBarIndicator.value = true
                    }
                }
            }

            //_myLocation.value = "My location is latitude: ${location!!.latitude}, longitude: ${location!!.longitude}"
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
                setWeatherIconPath(_weatherIconString, getApplication(application))

            }
        }
    }

    private fun setWeatherIconPath(_weatherIconString: MutableLiveData<String>, application: Application): Drawable? {

        when (_weatherIconString.value) {
            "01d" -> _weatherIconPath.value = application.getDrawable(R.drawable.clear_sky_01d)
            "01n" -> _weatherIconPath.value = application.getDrawable(R.drawable.clear_sky_01n)
            "02d" -> _weatherIconPath.value = application.getDrawable(R.drawable.few_clouds_02d)
            "02n" -> _weatherIconPath.value = application.getDrawable(R.drawable.few_clouds_02n)
            "03d" -> _weatherIconPath.value = application.getDrawable(R.drawable.scattered_clouds_03d)
            "03n" -> _weatherIconPath.value = application.getDrawable(R.drawable.scattered_clouds_03n)
            "04d" -> _weatherIconPath.value = application.getDrawable(R.drawable.broken_clouds_04d)
            "04n" -> _weatherIconPath.value = application.getDrawable(R.drawable.broken_clouds_04n)
            "09d" -> _weatherIconPath.value = application.getDrawable(R.drawable.shower_rain_09d)
            "09n" -> _weatherIconPath.value = application.getDrawable(R.drawable.shower_rain_09n)
            "10d" -> _weatherIconPath.value = application.getDrawable(R.drawable.rain_10d)
            "10n" -> _weatherIconPath.value = application.getDrawable(R.drawable.rain_10n)
            "11d" -> _weatherIconPath.value = application.getDrawable(R.drawable.thunderstorm_11d)
            "11n" -> _weatherIconPath.value = application.getDrawable(R.drawable.thunderstorm_11n)
            "13d" -> _weatherIconPath.value = application.getDrawable(R.drawable.snow_13d)
            "13n" -> _weatherIconPath.value = application.getDrawable(R.drawable.snow_13n)
            "50d" -> _weatherIconPath.value = application.getDrawable(R.drawable.mist_50d)
            "50n" -> _weatherIconPath.value = application.getDrawable(R.drawable.mist_50n)

            else -> {
                _weatherIconPath.value = application.getDrawable(R.drawable.ic_launcher_foreground)
            }
        }

        return _weatherIconPath.value
    }

    fun setCurrentLocationText(name: String){
        _currentLocationText.value = name
    }
}