package com.voye.favoriteweathercasts.domain.repository

import com.voye.favoriteweathercasts.data.responses.ApiResource
import com.voye.favoriteweathercasts.data.responses.LocationNameDto
import com.voye.favoriteweathercasts.domain.location.CoordinatesByLocationName
import com.voye.favoriteweathercasts.domain.location.LocationName
import com.voye.favoriteweathercasts.domain.weather.WeatherData


interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): ApiResource<WeatherData>
    suspend fun getReverseGeocoding(lat: Double, lon: Double): List<LocationName>
    suspend fun getCoordinatesByLocationName(q: String): List<CoordinatesByLocationName>
}