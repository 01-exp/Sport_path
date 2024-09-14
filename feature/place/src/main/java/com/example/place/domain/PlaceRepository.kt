package com.example.place.domain

import androidx.lifecycle.MutableLiveData

interface PlaceRepository {

    fun getPlaceOnline(
        fieldId: Int,
        date: String,
        placeOnlineMapMutableLiveData: MutableLiveData<Map<String, Int>>
    )

    fun setEntry(placeId: Int, time: String)
}