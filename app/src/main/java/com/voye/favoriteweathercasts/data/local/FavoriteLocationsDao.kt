package com.voye.favoriteweathercasts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the favorite_locations table.
 */


@Dao
interface FavoriteLocationsDao {
    /**
     * @return all favorite_locations.
     */

    @Query("SELECT * FROM favorite_locations")
    suspend fun getFavoriteLocations(): List<FavoriteLocationDTO>

    /**
     * @param favoriteLocationID the id of the location
     * @return the location object with the locationID
     */

    @Query("SELECT * FROM favorite_locations where entry_id = :favoriteLocationId")
    suspend fun getFavoriteLocationsById(favoriteLocationId: String): FavoriteLocationDTO

    /**
     * Insert a favoriteLocation in the database. If the favoriteLocation already exists, replace it.
     *
     * @param favoriteLocation the location to be inserted.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteLocation(favoriteLocation: FavoriteLocationDTO)

    /**
     * Delete all favoriteLocations.
     */

    @Query("DELETE FROM favorite_locations")
    suspend fun deleteAllFavoriteLocations()

    /**
     * Delete selected favoriteLocation by Id.
     */

    @Query("DELETE FROM favorite_locations WHERE entry_id = :favoriteLocationId")
    suspend fun deleteFavoriteLocationById(favoriteLocationId: String)

}