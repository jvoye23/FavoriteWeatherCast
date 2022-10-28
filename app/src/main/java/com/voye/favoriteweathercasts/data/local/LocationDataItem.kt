package com.voye.favoriteweathercasts.data.local

import java.io.Serializable
import java.util.*

/**
 * data class acts as a data mapper between the DB and the UI
 */
data class LocationDataItem(
    var city: String?,
    var country: String?,
    var latitude: Double?,
    var longitude: Double?,
    var created: String?,
    val id: String = UUID.randomUUID().toString()
) : Serializable
