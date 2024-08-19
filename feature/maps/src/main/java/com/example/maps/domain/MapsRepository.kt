package com.example.maps.domain

import androidx.lifecycle.MutableLiveData
import com.example.maps.data.data_structures.Place
import com.example.maps.data.data_structures.Sport

interface MapsRepository {
    fun getPlacesOnSport(placeList: MutableLiveData<List<Place>>)
    fun getCurrentSport(): Sport
    fun setCurrentSport(sportName: String)

}