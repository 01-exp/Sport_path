package com.example.maps.data

import com.example.maps.data.data_structures.Sport

interface MapsStorage {
    val defaultValue: String

    fun saveCurrentSport(sportName: String)

    fun getCurrentSport(): Sport

}