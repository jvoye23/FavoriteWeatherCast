package com.voye.favoriteweathercasts.domain.repository

import com.voye.favoriteweathercasts.data.local.FavoriteLocationDTO
import com.voye.favoriteweathercasts.domain.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * Main entry point for accessing favoriteLocations data from Room DB
 */

interface FavoriteLocationRepository {
    fun getFavoriteLocations(): Result<Flow<List<FavoriteLocationDTO>>>
    suspend fun saveFavoriteLocation(location: FavoriteLocationDTO)
    suspend fun getFavoriteLocation(id: String): Result<FavoriteLocationDTO>
    suspend fun deleteFavoriteLocation(id: String): Result<Unit>
    suspend fun deleteAllFavoriteLocations()
}