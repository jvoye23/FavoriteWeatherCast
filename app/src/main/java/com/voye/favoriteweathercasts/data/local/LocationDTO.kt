package com.voye.favoriteweathercasts.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

/**
 * Immutable model class for a Location. In order to compile with Room
 *
 * @param cityAndCountry    city name of the location
 * @param latitude          latitude of the location
 * @param longitude         longitude of the location
 * @param created           LocalDateTime of creation
 * @param id                id of the reminder
 */

@Entity(tableName = "locations")
data class LocationDTO(
    @ColumnInfo(name = "city") var city: String?,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "latitude") var latitude: Double?,
    @ColumnInfo(name = "longitude") var longitude: Double?,
    @ColumnInfo(name = "created") var created: String?,
    @PrimaryKey @ColumnInfo(name = "entry_id") val id: String = UUID.randomUUID().toString()

)