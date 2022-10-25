package com.voye.favoriteweathercasts.presentation

import com.voye.favoriteweathercasts.domain.weather.WeatherData

data class WeatherState (
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)
