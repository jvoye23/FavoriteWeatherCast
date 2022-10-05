package com.voye.favoriteweathercasts.data.mappers


import com.voye.favoriteweathercasts.data.responses.CoordinatesByLocationNameDto
import com.voye.favoriteweathercasts.data.responses.LocationNameDto
import com.voye.favoriteweathercasts.domain.location.CoordinatesByLocationName
import com.voye.favoriteweathercasts.domain.location.LocationName

fun List<LocationNameDto>.toLocationNameInfo(): List<LocationName> {
    return mapIndexed { _, locationNameDto ->
        val name = locationNameDto.name
        val country = locationNameDto.country
        LocationName(
            name = name,
            country = country
        )
    }
}

fun List<CoordinatesByLocationNameDto>.toCoordinatesByLocationNameInfo(): List<CoordinatesByLocationName>{
    return mapIndexed { index, coordinatesByLocationNameDto ->
        val name = coordinatesByLocationNameDto.name
        val lat = coordinatesByLocationNameDto.lat
        val lon = coordinatesByLocationNameDto.lon
        val country = coordinatesByLocationNameDto.country
        val state = coordinatesByLocationNameDto.state
        CoordinatesByLocationName(
            name = name,
            lat = lat,
            lon = lon,
            country = country,
            state = state
        )
    }
}

