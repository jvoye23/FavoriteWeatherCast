package com.voye.favoriteweathercasts.data.responses

data class CoordinatesByLocationNameDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)


