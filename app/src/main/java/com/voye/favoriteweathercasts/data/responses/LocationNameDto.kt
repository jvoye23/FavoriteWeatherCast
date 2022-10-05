package com.voye.favoriteweathercasts.data.responses


import com.squareup.moshi.Json

data class LocationNameDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "country")
    val country: String
)




