package com.voye.favoriteweathercasts.data.repository

import com.voye.favoriteweathercasts.data.mappers.toCoordinatesByLocationNameInfo
import com.voye.favoriteweathercasts.data.mappers.toDomain
import com.voye.favoriteweathercasts.data.mappers.toLocationNameInfo
import com.voye.favoriteweathercasts.data.remote.WeatherApi
import com.voye.favoriteweathercasts.data.responses.LocationNameDto
import com.voye.favoriteweathercasts.data.responses.safeApiCall
import com.voye.favoriteweathercasts.domain.location.CoordinatesByLocationName
import com.voye.favoriteweathercasts.domain.location.LocationName
import com.voye.favoriteweathercasts.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiService: WeatherApi):
    WeatherRepository {
    override suspend fun getWeatherData(lat: Double, lon: Double) = safeApiCall {
        apiService.getWeatherData(
            lat = lat,
            lon = lon
        ).toDomain()
    }

    override suspend fun getReverseGeocoding(lat: Double, lon: Double): List<LocationName> {
        return apiService.getLocationName(
            lat = lat,
            lon = lon
        ).toLocationNameInfo()

    }

    override suspend fun getCoordinatesByLocationName(q: String): List<CoordinatesByLocationName> {
        return apiService.getCoordinatesByLocationName(
            q = q
        ).toCoordinatesByLocationNameInfo()
    }
}
