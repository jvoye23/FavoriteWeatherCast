package com.voye.favoriteweathercasts.forecasts

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.remote.*
import com.voye.favoriteweathercasts.presentation.WeatherViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
class ForecastViewModel(application: Application) : AndroidViewModel(application) {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    private val _dailyMaxTemperature = MutableLiveData<Double>()
    private val _todayMinTemperature = MutableLiveData<Int>()
    private val _currentTemperature = MutableLiveData<Int>()
    private val _currentFeelsLike = MutableLiveData<Int>()
    private val _todayMaxTemperature = MutableLiveData<Int>()
    private val _weatherIconString = MutableLiveData<String>()
    private val _weatherIconPath = MutableLiveData<Drawable>()
    private val _weatherDescription = MutableLiveData<String>()
    private val _hourlyForecastList = MutableLiveData<List<Hourly>>()


    var dailyMaxTempList: MutableList<Double> = mutableListOf()
    var dailyMinTempList: MutableList<Double> = mutableListOf()



    //@RequiresApi(Build.VERSION_CODES.O)
    //val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)


    val currentDateTime: String
        get() = getCurrentTime()

    // The external immutable LiveData for the response String

    val response: LiveData<String>
        get() = _response

    val currentTemperature: LiveData<Int>
        get() = _currentTemperature

    val currentFeelsLike: LiveData<Int>
        get() = _currentFeelsLike

    val todayMaxTemperature: LiveData<Int>
        get() = _todayMaxTemperature

    val todayMinTemperature: LiveData<Int>
        get() = _todayMinTemperature

    val weatherIconString: LiveData<String>
        get() = _weatherIconString

    val weatherIconPath: LiveData<Drawable>
        get() = _weatherIconPath

    val weatherDescription: LiveData<String>
        get() = _weatherDescription

    val hourlyForecastList: LiveData<List<Hourly>>
        get() = _hourlyForecastList




    /**
     * Call getWeatherData() on init so the data will display immediately.
     */
    init {
        GlobalScope.launch {
            getWeatherData()
        }

    }

    fun getCurrentTime(): String {
        val myDateTime: String
        val currentTime = LocalDateTime.now()
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MMMM, HH:mm")
        myDateTime = "${currentTime.format(timeFormatter)} "

        return myDateTime
    }



    private fun getWeatherData() {
        OldWeatherApi.retrofitService.getProperties().enqueue( object: Callback<WeatherMainModel> {
            override fun onFailure(call: Call<WeatherMainModel>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<WeatherMainModel>, response: Response<WeatherMainModel>) {
                _response.value = "Success: ${response.body()?.current?.sunrise}"
                //_myWeather.value = response.body()

                val networkWeather = response.body()

                _currentTemperature.value = networkWeather?.current?.temp?.let { round(it).toInt() }
                _currentFeelsLike.value = networkWeather?.current?.feels_like?.let { round(it).toInt() }

                _hourlyForecastList.value = networkWeather?.hourly



                networkWeather?.daily?.forEachIndexed { index, daily ->
                    dailyMaxTempList.add(index, daily.temp.max)
                    Log.d("MAXTEMP", "Max Temperature: $index,  ${daily.temp.max}")
                }

                networkWeather?.daily?.forEachIndexed { index, daily ->
                    dailyMinTempList.add(index, daily.temp.min)
                }

                _todayMaxTemperature.value = dailyMaxTempList[0].toInt()
                _todayMinTemperature.value = dailyMinTempList[0].toInt()


                networkWeather?.current!!.weather.forEachIndexed { index, weather ->

                    _weatherIconString.value = weather.icon
                    _weatherDescription.value = weather.description
                    setWeatherIconPath(_weatherIconString, getApplication())
                }
            }
        })
    }

    private fun setWeatherIconPath(_weatherIconString: MutableLiveData<String>, application: Application): Drawable? {

        Log.d("SET ICON", "Icon value = ${_weatherIconString.value}")

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
        Log.d("Icon", "weatherIconPath = ${_weatherIconPath.value}")
        return _weatherIconPath.value

    }

}



