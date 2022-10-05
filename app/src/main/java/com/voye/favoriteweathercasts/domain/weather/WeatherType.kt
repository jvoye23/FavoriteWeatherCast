package com.voye.favoriteweathercasts.domain.weather

import androidx.annotation.DrawableRes
import com.voye.favoriteweathercasts.R

sealed class WeatherType(
    val main: String,
    val description: String,
    @DrawableRes val iconRes: Int

) {
    object ThunderstormWithLightRain: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm with light rain",
        iconRes = R.drawable.thunderstorm_11d
    )
    object ThunderstormWithRain: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm with rain",
        iconRes = R.drawable.thunderstorm_11d
    )
    object ThunderstormWithHeavyRain: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm with heavy rain",
        iconRes = R.drawable.thunderstorm_11d
    )
    object LightThunderstorm: WeatherType(
        main= "Thunderstorm",
        description = "light thunderstorm",
        iconRes = R.drawable.thunderstorm_11d
    )
    object Thunderstorm: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm",
        iconRes = R.drawable.thunderstorm_11d
    )
    object HeavyThunderstorm: WeatherType(
        main= "Thunderstorm",
        description = "heavy thunderstorm",
        iconRes = R.drawable.thunderstorm_11d
    )
    object RaggedThunderstorm: WeatherType(
        main= "Thunderstorm",
        description = "ragged thunderstorm",
        iconRes = R.drawable.thunderstorm_11d
    )
    object ThunderstormWithLightDrizzle: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm with light drizzle",
        iconRes = R.drawable.thunderstorm_11d
    )
    object ThunderstormWithDrizzle: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm with drizzle",
        iconRes = R.drawable.thunderstorm_11d
    )
    object ThunderstormWithHeavyDrizzle: WeatherType(
        main= "Thunderstorm",
        description = "thunderstorm with heavy drizzle",
        iconRes = R.drawable.thunderstorm_11d
    )
    object LightIntensityDrizzle: WeatherType(
        main= "Drizzle",
        description = "light intensity drizzle",
        iconRes = R.drawable.shower_rain_09d
    )
    object Drizzle: WeatherType(
        main= "Drizzle",
        description = "drizzle",
        iconRes = R.drawable.shower_rain_09d
    )
    object HeavyIntensityDrizzle: WeatherType(
        main= "Drizzle",
        description = "heavy intensity drizzle",
        iconRes = R.drawable.shower_rain_09d
    )
    object LightIntensityDrizzleRain: WeatherType(
        main= "Drizzle",
        description = "light intensity drizzle rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object DrizzleRain: WeatherType(
        main= "Drizzle",
        description = "drizzle rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object HeavyIntensityDrizzleRain: WeatherType(
        main= "Drizzle",
        description = "heavy intensity drizzle rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object ShowerRainAndDrizzle: WeatherType(
        main= "Drizzle",
        description = "shower rain and drizzle",
        iconRes = R.drawable.shower_rain_09d
    )
    object HeavyShowerRainAndDrizzle: WeatherType(
        main= "Drizzle",
        description = "heavy shower rain and drizzle",
        iconRes = R.drawable.shower_rain_09d
    )
    object ShowerDrizzle: WeatherType(
        main= "Drizzle",
        description = "shower drizzle",
        iconRes = R.drawable.shower_rain_09d
    )
    object LightRain: WeatherType(
        main= "Rain",
        description = "light rain",
        iconRes = R.drawable.rain_10d
    )
    object ModerateRain: WeatherType(
        main= "Rain",
        description = "moderate rain",
        iconRes = R.drawable.rain_10d
    )
    object HeavyIntensityRain: WeatherType(
        main= "Rain",
        description = "light rain",
        iconRes = R.drawable.rain_10d
    )
    object VeryHeavyRain: WeatherType(
        main= "Rain",
        description = "very heavy rain",
        iconRes = R.drawable.rain_10d
    )
    object ExtremeRain: WeatherType(
        main= "Rain",
        description = "extreme rain",
        iconRes = R.drawable.rain_10d
    )
    object FreezingRain: WeatherType(
        main= "Rain",
        description = "freezing rain",
        iconRes = R.drawable.snow_13d
    )
    object LightIntensityShowerRain: WeatherType(
        main= "Rain",
        description = "light intensity shower rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object ShowerRain: WeatherType(
        main= "Rain",
        description = "shower rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object HeavyIntensityShowerRain: WeatherType(
        main= "Rain",
        description = "heavy intensity shower rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object RaggedShowerRain: WeatherType(
        main= "Rain",
        description = "ragged shower rain",
        iconRes = R.drawable.shower_rain_09d
    )
    object LightSnow: WeatherType(
        main= "Snow",
        description = "light snow",
        iconRes = R.drawable.snow_13d
    )
    object Snow: WeatherType(
        main= "Snow",
        description = "snow",
        iconRes = R.drawable.snow_13d
    )
    object HeavySnow: WeatherType(
        main= "Snow",
        description = "heavy snow",
        iconRes = R.drawable.snow_13d
    )
    object Sleet: WeatherType(
        main= "Snow",
        description = "Sleet",
        iconRes = R.drawable.snow_13d
    )
    object LightShowerSleet: WeatherType(
        main= "Snow",
        description = "light shower sleet",
        iconRes = R.drawable.snow_13d
    )
    object ShowerSleet: WeatherType(
        main= "Snow",
        description = "shower sleet",
        iconRes = R.drawable.snow_13d
    )
    object LightRainAndSnow: WeatherType(
        main= "Snow",
        description = "light rain and snow",
        iconRes = R.drawable.snow_13d
    )
    object RainAndSnow: WeatherType(
        main= "Snow",
        description = "rain and snow",
        iconRes = R.drawable.snow_13d
    )
    object LightShowerSnow: WeatherType(
        main= "Snow",
        description = "light shower snow",
        iconRes = R.drawable.snow_13d
    )
    object ShowerSnow: WeatherType(
        main= "Snow",
        description = "shower snow",
        iconRes = R.drawable.snow_13d
    )
    object HeavyShowerSnow: WeatherType(
        main= "Snow",
        description = "heavy shower snow",
        iconRes = R.drawable.snow_13d
    )
    object Mist: WeatherType(
        main= "Mist",
        description = "mist",
        iconRes = R.drawable.mist_50d
    )
    object Smoke: WeatherType(
        main= "Smoke",
        description = "smoke",
        iconRes = R.drawable.mist_50d
    )
    object Haze: WeatherType(
        main= "Haze",
        description = "haze",
        iconRes = R.drawable.mist_50d
    )
    object DustWhirls: WeatherType(
        main= "Dust",
        description = "sand/ dust whirls",
        iconRes = R.drawable.mist_50d
    )
    object Fog: WeatherType(
        main= "Fog",
        description = "fog",
        iconRes = R.drawable.mist_50d
    )
    object Sand: WeatherType(
        main= "Sand",
        description = "sand",
        iconRes = R.drawable.mist_50d
    )
    object Dust: WeatherType(
        main= "Dust",
        description = "dust",
        iconRes = R.drawable.mist_50d
    )
    object Ash: WeatherType(
        main= "Ash",
        description = "volcanic ash",
        iconRes = R.drawable.mist_50d
    )
    object Squall: WeatherType(
        main= "Squall",
        description = "squalls",
        iconRes = R.drawable.mist_50d
    )
    object Tornado: WeatherType(
        main= "Tornado",
        description = "tornado",
        iconRes = R.drawable.mist_50d
    )
    object Clear: WeatherType(
        main= "Clear",
        description = "clear sky",
        iconRes = when("icon"){
            "01d" -> R.drawable.clear_sky_01d
            "01n" -> R.drawable.clear_sky_01n
            else -> R.drawable.clear_sky_01d
        }
    )
    object FewClouds: WeatherType(
        main= "Clouds",
        description = "few clouds: 11-25%",
        iconRes = R.drawable.few_clouds_02d
    )
    object ScatteredClouds: WeatherType(
        main= "Clouds",
        description = "scattered clouds: 25-50%",
        iconRes = R.drawable.scattered_clouds_03d
    )
    object BrokenClouds: WeatherType(
        main= "Clouds",
        description = "broken clouds: 51-84%",
        iconRes = R.drawable.broken_clouds_04d
    )
    object OverCastClouds: WeatherType(
        main= "Clouds",
        description = "overcast clouds: 85-100%",
        iconRes = R.drawable.mist_50d
    )

    companion object {
        fun fromOpenWeatherMap(code: Int): WeatherType {
            return when(code){
                200 -> ThunderstormWithLightRain
                201 -> ThunderstormWithRain
                202 -> ThunderstormWithHeavyRain
                210 -> LightThunderstorm
                211 -> Thunderstorm
                212 -> HeavyThunderstorm
                221 -> RaggedThunderstorm
                230 -> ThunderstormWithLightDrizzle
                231 -> ThunderstormWithDrizzle
                232 -> ThunderstormWithHeavyDrizzle
                300 -> LightIntensityDrizzle
                301 -> Drizzle
                302 -> HeavyIntensityDrizzle
                310 -> LightIntensityDrizzleRain
                311 -> DrizzleRain
                312 -> HeavyIntensityDrizzleRain
                313 -> ShowerRainAndDrizzle
                314 -> HeavyShowerRainAndDrizzle
                321 -> ShowerDrizzle
                500 -> LightRain
                501 -> ModerateRain
                502 -> HeavyIntensityRain
                503 -> VeryHeavyRain
                504 -> ExtremeRain
                511 -> FreezingRain
                520 -> LightIntensityShowerRain
                521 -> ShowerRain
                522 -> HeavyIntensityShowerRain
                531 -> RaggedShowerRain
                600 -> LightSnow
                601 -> Snow
                602 -> HeavySnow
                611 -> Sleet
                612 -> LightShowerSleet
                613 -> ShowerSleet
                615 -> LightRainAndSnow
                616 -> RainAndSnow
                620 -> LightShowerSnow
                621 -> ShowerSnow
                622 -> HeavyShowerSnow
                701 -> Mist
                711 -> Smoke
                721 -> Haze
                731 -> DustWhirls
                741 -> Fog
                751 -> Sand
                761 -> Dust
                762 -> Ash
                771 -> Squall
                781 -> Tornado
                801 -> FewClouds
                802 -> ScatteredClouds
                803 -> BrokenClouds
                804 -> OverCastClouds
                else -> Thunderstorm

            }
        }
    }


}
