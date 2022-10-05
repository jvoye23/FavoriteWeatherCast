package com.voye.favoriteweathercasts.data.responses

import com.squareup.moshi.Json

data class WeatherDto(
    //Geographical coordinates of the location (latitude)
    val lat: Double,

    //Geographical coordinates of the location (longitude)
    val lon: Double,

    //Timezone name for the requested location
    val timezone: String,

    //Shift in seconds from UTC
    val timezone_offset: Int,

    //Data point dt refers to the requested time, rather than the current time
    val current: CurrentDataDto,

    val hourly: List<HourlyDataDto>,

    val daily: List<DailyDataDto>
)

data class CurrentDataDto(
    //Current time, Unix, UTC
    val dt: Int,

    //Sunrise time, Unix, UTC
    val sunrise: Int,

    //Sunset time, Unix, UTC
    val sunset: Int,

    //Temperature. Units - default: kelvin, metric: Celsius, imperial: Fahrenheit
    val temp: Double,

    //Temperature. This temperature parameter accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    val feels_like: Double,

    //Atmospheric pressure on the sea level, hPa
    val pressure: Int,

    //Humidity, %
    val humidity: Int,

    //Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val dew_point: Double,

    //Cloudiness, %
    val clouds: Int,

    //Current UV index
    val uvi: Double,

    // Average visibility, metres. The maximum value of the visibility is 10km
    val visibility: Int,

    //Wind speed. Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
    val wind_speed: Double,

    //Wind speed. Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
    val wind_gust: String?,

    // Wind direction, degrees (meteorological)
    val wind_deg: Int,

    //(where available) Rain volume for last hour, mm
    val lastHourRain: Double?,

    //(where available) Snow volume for last hour, mm
    val lastHourSnow: Double?,

    val weather: List<WeatherDataDto>
)

data class HourlyDataDto(
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
    val wind_deg: Int,
    val wind_gust: Double,
    val pop: Double,
    val rain: RainDataDto?,
    val snow: SnowDataDto?,
    val weather: List<WeatherDataDto>
)

data class DailyDataDto(
//Time of the forecasted data, Unix, UTC
    val dt: Long,

    //Sunrise time, Unix, UTC
    val sunrise: Long,

    //Sunset time, Unix, UTC
    val sunset: Long,

    //The time of when the moon rises for this day, Unix, UTC
    val moonrise: Long,

    //The time of when the moon sets for this day, Unix, UTC
    val moonset: Long,

    //Moon phase. 0 and 1 are 'new moon', 0.25 is 'first quarter moon', 0.5 is 'full moon' and 0.75 is 'last quarter moon'. The periods in between are called 'waxing crescent', 'waxing gibous', 'waning gibous', and 'waning crescent', respectively.
    val moon_phase: Double,

    //Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val temp: DailyTempDataDto,

    //This accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    val feels_like: DailyFeelsLikeDataDto,

    //Atmospheric pressure on the sea level, hPa
    val pressure: Int,

    //Humidity, %
    val humidity: Int,

    // Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    val dew_point: Double,

    //Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    val wind_speed: Double,

    //(where available) Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    val wind_gust: Double,

    //Wind direction, degrees (meteorological)
    val wind_deg: Int,

    //Cloudiness, %
    val clouds: Int,

    //The maximum value of UV index for the day
    val uvi: Double,

    //Probability of precipitation. The values of the parameter vary between 0 and 1, where 0 is equal to 0%, 1 is equal to 100%
    val pop: Double,

    //(where available) Precipitation volume, mm
    val rain: Double?,

    //(where available) Snow volume, mm
    val snow: Double?,

    val weather: List<WeatherDataDto>,
)

data class WeatherDataDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class DailyTempDataDto(
    //Morning temperature
    val morn: Double,

    //Day temperature
    val day: Double,

    //Evening temperature
    val eve: Double,

    //Night temperature
    val night: Double,

    //Min daily temperature
    val min: Double,

    //Max daily temperature
    val max: Double
)

data class DailyFeelsLikeDataDto(
    //Morning temperature
    val morn: Double,

    //Day temperature
    val day: Double,

    //Evening temperature
    val eve: Double,

    //Night temperature
    val night: Double,
)

data class RainDataDto(
    @Json(name = "1h")val lastHourRain: Double?
)

data class SnowDataDto(
    @Json(name = "1h")val lastHourSnow: Double?
)
