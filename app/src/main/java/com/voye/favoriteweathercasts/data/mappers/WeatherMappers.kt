package com.voye.favoriteweathercasts.data.mappers

import com.voye.favoriteweathercasts.data.responses.*
import com.voye.favoriteweathercasts.domain.weather.*
import com.voye.favoriteweathercasts.domain.weather.Current
import com.voye.favoriteweathercasts.domain.weather.Daily
import com.voye.favoriteweathercasts.domain.weather.DailyFeelsLike
import com.voye.favoriteweathercasts.domain.weather.DailyTemp
import com.voye.favoriteweathercasts.domain.weather.Weather


fun WeatherDto.toDomain() = WeatherData(
    lat = lat,
    lon = lon,
    timezone = timezone,
    timezone_offset = timezone_offset,
    current = current.toDomain(),
    hourly = hourly.map { it.toDomain() },
    daily = daily.map { it.toDomain() },

)

fun CurrentDataDto.toDomain() = Current(
    dt = dt,
    sunrise = sunrise,
    sunset = sunset,
    temp = temp,
    feels_like = feels_like,
    pressure = pressure,
    humidity = humidity,
    dew_point = dew_point,
    clouds = clouds,
    uvi = uvi,
    visibility = visibility,
    wind_speed = wind_speed,
    wind_gust = wind_gust,
    wind_deg = wind_deg,
    lastHourRain = lastHourRain,
    lastHourSnow = lastHourSnow,
    weather = weather.map { it.toDomain() }
)

fun HourlyDataDto.toDomain() = com.voye.favoriteweathercasts.domain.weather.Hourly(
    dt = dt,
    temp = temp,
    feels_like = feels_like,
    pressure = pressure,
    humidity = humidity,
    dew_point = dew_point,
    uvi = uvi,
    clouds = clouds,
    visibility = visibility,
    wind_speed = wind_speed,
    wind_gust = wind_gust,
    wind_deg = wind_deg,
    pop = pop,
    rain = rain?.lastHourRain,
    snow = snow?.lastHourSnow,
    weather = weather.map { it.toDomain()}
)

fun WeatherDataDto.toDomain() = Weather(
    id = id,
    main = main,
    description = description,
    icon = icon
)

fun DailyDataDto.toDomain() = Daily(
    dt = dt,
    sunrise = sunrise,
    sunset = sunset,
    moonrise = moonrise,
    moonset = moonset,
    moon_phase = moon_phase,
    temp = temp.toDomain(),
    feels_like = feels_like.toDomain(),
    pressure = pressure,
    humidity = humidity,
    dew_point = dew_point,
    wind_speed = wind_speed,
    wind_gust = wind_gust,
    wind_deg = wind_deg,
    clouds = clouds,
    uvi = uvi,
    pop = pop,
    rain = rain,
    snow = snow,
    weather = weather.map { it.toDomain() }
)

fun DailyTempDataDto.toDomain() = DailyTemp(
    morn = morn,
    day = day,
    eve = eve,
    night = night,
    min = min,
    max = max
)

fun DailyFeelsLikeDataDto.toDomain() = DailyFeelsLike(
    morn = morn,
    day = day,
    eve = eve,
    night = night
)











