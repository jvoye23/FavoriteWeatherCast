package com.voye.favoriteweathercasts.domain.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class SearchTesting() {

    var testList = mutableListOf<CoordinatesByLocationName>()
    private val _filteredList = MutableLiveData<List<CoordinatesByLocationName>>()
        val filteredList: LiveData<List<CoordinatesByLocationName>>
            get() = _filteredList

    init {
        testList.add(0, CoordinatesByLocationName(name = "Paris", country = "FR", lat = 10.1, lon = 52.5, state = "Paris"))
        testList.add(1, CoordinatesByLocationName(name = "Hamburg", country = "DE", lat = 10.1, lon = 52.5, state = "Paris"))
        testList.add(2, CoordinatesByLocationName(name = "Berlin", country = "DE", lat = 10.1, lon = 52.5, state = "Paris"))
        testList.add(3, CoordinatesByLocationName(name = "New York", country = "USA", lat = 10.1, lon = 52.5, state = "Paris"))
        testList.add(4, CoordinatesByLocationName(name = "Los Angeles", country = "FR", lat = 10.1, lon = 52.5, state = "Paris"))
        testList.add(5, CoordinatesByLocationName(name = "Lübeck", country = "DE", lat = 10.1, lon = 52.5, state = "Paris"))
    }

    fun filterMyList(query: String): List<CoordinatesByLocationName>{
        _filteredList.value = testList.filter {
            it.name == query
        }
        return _filteredList.value!!
    }






}



