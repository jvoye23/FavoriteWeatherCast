package com.voye.favoriteweathercasts.data.remote

import com.voye.favoriteweathercasts.data.responses.CoordinatesByLocationNameDto
import com.voye.favoriteweathercasts.data.responses.LocationNameDto
import com.voye.favoriteweathercasts.data.responses.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/3.0/onecall?exclude=minutely&units=metric&appid=944b843df4668f3251f793830ccc985d")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherDto

    @GET("geo/1.0/reverse?limit=5&appid=944b843df4668f3251f793830ccc985d")
    suspend fun getLocationName(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): List<LocationNameDto>

    @GET("geo/1.0/direct?limit=5&appid=944b843df4668f3251f793830ccc985d")
    suspend fun getCoordinatesByLocationName(
        @Query("q") q: String
    ): List<CoordinatesByLocationNameDto>

}