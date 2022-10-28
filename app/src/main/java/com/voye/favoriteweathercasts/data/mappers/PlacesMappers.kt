package com.voye.favoriteweathercasts.data.mappers

import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationDataItem



fun LocationDTO.toDomain() = LocationDataItem(
    city = city,
    country = country,
    latitude = latitude,
    longitude = longitude,
    created = created
)