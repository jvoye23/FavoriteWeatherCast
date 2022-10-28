package com.voye.favoriteweathercasts.data.repository

import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationsDao
import com.voye.favoriteweathercasts.domain.repository.LocationRepository
import com.voye.favoriteweathercasts.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Concrete implementation of a data source as a db.
 *
 * @param locationsDao the dao that does the Room db operations
 * @param ioDispatcher a coroutine dispatcher to offload the blocking IO tasks
 */

class LocalLocationsRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao
): LocationRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    /**
     * Get the locations list from the local db
     * @return Result the holds a Success with all the locations or an Error object with the error message
     */
    override suspend fun getLocations(): Result<List<LocationDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(locationsDao.getLocations())
        }catch (ex: Exception){
            Result.Error(ex.localizedMessage)
        }
    }


    /**
     * Get a location by its id
     * @param id to be used to get the location
     * @return Result the holds a Success object with the Location or an Error object with the error message
     */
    override suspend fun getLocation(id: String): Result<LocationDTO> = withContext(ioDispatcher) {
        try {
            val location = locationsDao.getLocationsById(id)
            if (location != null){
                return@withContext Result.Success(location)
            } else {
                return@withContext Result.Error("Location not found")
            }

        } catch (e: Exception){
            return@withContext Result.Error(e.localizedMessage)
        }
    }


    /**
     * Insert a location in the db.
     * @param location the location to be inserted
     */
    override suspend fun saveLocation(location: LocationDTO) =
        withContext(ioDispatcher){
        locationsDao.saveLocation(location)
    }


    /**
     * Deletes all the reminders in the db
     */
    override suspend fun deleteAllLocations() {
        withContext(ioDispatcher){
            locationsDao.deleteAllLocations()
        }
    }
}