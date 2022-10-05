package com.voye.favoriteweathercasts.domain.location

data class CoordinatesByLocationName(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)
