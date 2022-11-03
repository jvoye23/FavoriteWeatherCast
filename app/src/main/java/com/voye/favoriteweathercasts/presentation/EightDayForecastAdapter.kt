package com.voye.favoriteweathercasts.presentation

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
import com.voye.favoriteweathercasts.domain.weather.Daily
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class EightDayForecastAdapter: RecyclerView.Adapter<EightDayForecastAdapter.ViewHolder>() {

    private val diffUtil = object: DiffUtil.ItemCallback<Daily>(){
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(dataResponse: List<Daily>){
        asyncListDiffer.submitList(dataResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): EightDayForecastAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_daily_forecast, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EightDayForecastAdapter.ViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d. MMM")
        val dayOfTheWeek = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.dt))
        val localZoneDateTime = ZonedDateTime.parse(dayOfTheWeek).withZoneSameInstant(ZoneId.systemDefault())

        holder.dailyDate.text = localZoneDateTime.format(timeFormatter)

        holder.dailyDescription.text = data.weather[0].description
        holder.dailyMaxTemperature.text = data.temp.max.toInt().toString() + "°C"
        holder.dailyMinTemperature.text = data.temp.min.toInt().toString() + "°C"
        //holder.dailyRain.text = data.rain?.toInt().toString() + "%"
        holder.dailyRain.text = "${(data.pop * 100).toInt()}" + "%"

        holder.dailyWeatherIcon.setImageResource(when(data.weather[0].icon){
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

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dailyDate: TextView = itemView.findViewById(R.id.date_textView)
        val dailyRain: TextView = itemView.findViewById(R.id.daily_rain_textView)
        val dailyDescription: TextView = itemView.findViewById(R.id.weather_description_textView)
        val dailyWeatherIcon: ImageView = itemView.findViewById(R.id.daily_weather_icon)
        val dailyMaxTemperature: TextView = itemView.findViewById(R.id.daily_max_temperature_textView)
        val dailyMinTemperature: TextView = itemView.findViewById(R.id.daily_min_temperature_textView)
    }
}