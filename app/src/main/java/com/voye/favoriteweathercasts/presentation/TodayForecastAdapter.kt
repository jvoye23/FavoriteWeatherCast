package com.voye.favoriteweathercasts.forecasts

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.remote.Hourly
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round

class TodayForecastAdapter: RecyclerView.Adapter<TodayForecastAdapter.ViewHolder>()  {

    private val diffUtil = object: DiffUtil.ItemCallback<com.voye.favoriteweathercasts.domain.weather.Hourly>(){
        override fun areItemsTheSame(
            oldItem: com.voye.favoriteweathercasts.domain.weather.Hourly,
            newItem: com.voye.favoriteweathercasts.domain.weather.Hourly
        ): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(
            oldItem: com.voye.favoriteweathercasts.domain.weather.Hourly,
            newItem: com.voye.favoriteweathercasts.domain.weather.Hourly
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(dataResponse: List<com.voye.favoriteweathercasts.domain.weather.Hourly>){
        asyncListDiffer.submitList(dataResponse)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_hourly_forecast, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.hourlyTemperature.text = data.temp.let { round(it).toInt() }.toString() + " °C"

        //2022-05-24T11:00:00Z
        val hour = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.dt))
        val localZoneDateTime = ZonedDateTime.parse(hour).withZoneSameInstant(ZoneId.systemDefault())
        holder.hour.text = localZoneDateTime.toString().substring(11, 16)
        holder.hourlyDescription.text = data.weather[0].description
        holder.hourlyRain.text = "${(data.pop * 100).toInt()}" + "%"
        holder.hourlyWeatherIcon.setImageResource(when(data.weather[0].icon) {
            "01d" -> R.drawable.clear_sky_01d
            "01n" -> R.drawable.clear_sky_01n
            "02d" -> R.drawable.few_clouds_02d
            "02n" -> R.drawable.few_clouds_02n
            "03d" -> R.drawable.scattered_clouds_03d
            "03n" -> R.drawable.scattered_clouds_03n
            "04d" -> R.drawable.broken_clouds_04d
            "04n" -> R.drawable.broken_clouds_04n
            "09d" -> R.drawable.shower_rain_09d
            "09n" -> R.drawable.shower_rain_09n
            "10d" -> R.drawable.rain_10d
            "10n" -> R.drawable.rain_10n
            "11d" -> R.drawable.thunderstorm_11d
            "11n" -> R.drawable.thunderstorm_11n
            "13d" -> R.drawable.snow_13d
            "13n" -> R.drawable.snow_13n
            "50d" -> R.drawable.mist_50d
            "50n" -> R.drawable.mist_50n
            else -> R.drawable.ic_launcher_foreground
        })
    }

    override fun getItemCount(): Int{
        return asyncListDiffer.currentList.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val hourlyWeatherIcon: ImageView = itemView.findViewById(R.id.hourly_weather_icon)
        val hourlyTemperature: TextView = itemView.findViewById(R.id.hourly_temperature_textView)
        val hour: TextView = itemView.findViewById(R.id.hour_textView)
        val hourlyDescription: TextView = itemView.findViewById(R.id.hourly_description_textView)
        val hourlyRain: TextView = itemView.findViewById(R.id.hourly_rain_textView)


    }
}

