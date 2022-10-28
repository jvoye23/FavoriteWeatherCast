package com.voye.favoriteweathercasts.data.repository

import com.voye.favoriteweathercasts.data.local.FavoriteLocationDTO
import com.voye.favoriteweathercasts.data.local.FavoriteLocationsDao
import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationsDao
import com.voye.favoriteweathercasts.domain.repository.FavoriteLocationRepository
import com.voye.favoriteweathercasts.domain.repository.LocationRepository
import com.voye.favoriteweathercasts.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Concrete implementation of a data source as a db.
 *
 * @param favoriteLocationsDao the dao that does the Room db operations
 * @param ioDispatcher a coroutine dispatcher to offload the blocking IO tasks
 */

class FavoriteLocalLocationsRepositoryImpl @Inject constructor(
    private val favoriteLocationsDao: FavoriteLocationsDao
): FavoriteLocationRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    /**
     * Get the favoriteLocations list from the local db
     * @return Result the holds a Success with all the favoriteLocation or an Error object with the error message
     */
    override suspend fun getFavoriteLocations(): Result<List<FavoriteLocationDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(favoriteLocationsDao.getFavoriteLocations())
        }catch (ex: Exception){
            Result.Error(ex.localizedMessage)
        }
    }


    /**
     * Get a favoriteLocation by its id
     * @param id to be used to get the favoriteLocation
     * @return Result the holds a Success object with the favoriteLocation or an Error object with the error message
     */
    override suspend fun getFavoriteLocation(id: String): Result<FavoriteLocationDTO> = withContext(ioDispatcher) {
        try {
            val favoriteLocation = favoriteLocationsDao.getFavoriteLocationsById(id)
            if (favoriteLocation != null){
                return@withContext Result.Success(favoriteLocation)
            } else {
                return@withContext Result.Error("Favorite Location not found")
            }

        } catch (e: Exception){
            return@withContext Result.Error(e.localizedMessage)
        }
    }


    /**
     * Insert a favoriteLocation in the db.
     * @param favoriteLocation the location to be inserted
     */
    override suspend fun saveFavoriteLocation(favoriteLocation: FavoriteLocationDTO) =
        withContext(ioDispatcher){
            favoriteLocationsDao.saveFavoriteLocation(favoriteLocation)
        }


    /**
     * Deletes all the favoriteLocations in the db
     */
    override suspend fun deleteAllFavoriteLocations() {
        withContext(ioDispatcher){
            favoriteLocationsDao.deleteAllFavoriteLocations()
        }
    }
}