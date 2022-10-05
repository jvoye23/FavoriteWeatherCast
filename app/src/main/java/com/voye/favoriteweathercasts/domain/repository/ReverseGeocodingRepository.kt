package com.voye.favoriteweathercasts.domain.repository

import com.voye.favoriteweathercasts.data.responses.ApiResource
import com.voye.favoriteweathercasts.domain.location.LocationName
import com.voye.favoriteweathercasts.domain.weather.WeatherData

interface ReverseGeocodingRepository {
    suspend fun getReverseGeocoding(lat: Double, lon: Double): ApiResource<LocationName>
}


