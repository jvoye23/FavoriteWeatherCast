package com.voye.favoriteweathercasts.di

import com.voye.favoriteweathercasts.data.repository.WeatherRepositoryImpl
import com.voye.favoriteweathercasts.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

   /* @Binds
    @Singleton
    abstract fun bindReverseGeocodingRepository(
        reverseGeocodingRepositoryImpl: ReverseGeocodingRepositoryImpl
    ): ReverseGeocodingRepository*/
}