package com.voye.favoriteweathercasts.domain.weather

data class HourlyWeatherInfo(
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_gust: Double,
    val wind_deg: Int,
    val pop: Double,
    val lastHourRain: Double?,
    val lastHourSnow: Double?,
    val weatherType: List<WeatherType>
)
