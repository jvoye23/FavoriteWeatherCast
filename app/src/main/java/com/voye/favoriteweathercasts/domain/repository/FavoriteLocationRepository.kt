package com.voye.favoriteweathercasts.domain.repository

import com.voye.favoriteweathercasts.data.local.FavoriteLocationDTO
import com.voye.favoriteweathercasts.domain.util.Result

/**
 * Main entry point for accessing favoriteLocations data from Room DB
 */

interface FavoriteLocationRepository {
    suspend fun getFavoriteLocations(): Result<List<FavoriteLocationDTO>>
    suspend fun saveFavoriteLocation(location: FavoriteLocationDTO)
    suspend fun getFavoriteLocation(id: String): Result<FavoriteLocationDTO>
    suspend fun deleteAllFavoriteLocations()
}