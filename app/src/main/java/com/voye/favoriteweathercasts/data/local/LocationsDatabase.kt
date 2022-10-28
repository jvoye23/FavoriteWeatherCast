package com.voye.favoriteweathercasts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The Room Database that contains the locations table.
 */

@Database(entities = [LocationDTO::class, FavoriteLocationDTO::class], version = 1, exportSchema = false)
abstract class LocationsDatabase: RoomDatabase(){

    /**
     * Connects the database to the DAO.
     */
    abstract fun locationDao(): LocationsDao
    abstract fun favoriteLocationDao(): FavoriteLocationsDao
}