package com.voye.favoriteweathercasts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Provides

/**
 * Data Access Object for the locations table.
 */


@Dao
interface LocationsDao {
    /**
     * @return all locations.
     */

    @Query("SELECT * FROM locations")
    suspend fun getLocations(): List<LocationDTO>

    /**
     * @param locationID the id of the location
     * @return the location object with the locationID
     */

    @Query("SELECT * FROM locations where entry_id = :locationId")
    suspend fun getLocationsById(locationId: String): LocationDTO

    /**
     * Insert a location in the database. If the location already exists, replace it.
     *
     * @param location the location to be inserted.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(location: LocationDTO)

    /**
     * Delete all locations.
     */

    @Query("DELETE FROM locations")
    suspend fun deleteAllLocations()

}