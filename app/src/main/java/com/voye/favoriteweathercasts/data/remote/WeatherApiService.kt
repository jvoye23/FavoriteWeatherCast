package com.voye.favoriteweathercasts.data.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.openweathermap.org/"


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
val jsonAdapter: JsonAdapter<WeatherMainModel> = moshi.adapter(WeatherMainModel::class.java)




/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object pointing to the desired URL
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface WeatherApiService {
    /**
     * Returns a Retrofit callback that delivers a String
     * The @GET annotation indicates that the endpoint that will be requested with the GET
     * HTTP method
     */

    @GET("data/3.0/onecall?lat=53.613230003662316&lon=10.014889269495548&exclude=minutely&units=metric&appid=944b843df4668f3251f793830ccc985d")
    fun getProperties(): Call<WeatherMainModel>

    /*@GET("data/2.5/onecall?lat=53.613230003662316&lon=10.014889269495548&exclude=minutely&units=metric&appid=944b843df4668f3251f793830ccc985d")
    suspend fun getProperties(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    )*/
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OldWeatherApi {
    val retrofitService : WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}

