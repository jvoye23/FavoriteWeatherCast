package com.voye.favoriteweathercasts.domain.repository

import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationsDao
import com.voye.favoriteweathercasts.domain.util.Result
import dagger.Provides


/**
 * Main entry point for accessing locations data from Room DB
 */

interface LocationRepository {
    suspend fun getLocations(): Result<List<LocationDTO>>
    suspend fun saveLocation(location: LocationDTO)
    suspend fun getLocation(id: String): Result<LocationDTO>
    suspend fun deleteAllLocations()
}