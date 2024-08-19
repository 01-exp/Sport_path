package com.example.maps.data.data_structures

import com.google.gson.annotations.SerializedName


data class Entry(
    @SerializedName("court_address")
    val placeAddress: String,
    @SerializedName("court_id")
    val placeId: Int,
    @SerializedName("court_sport")
    val placeSport: String,
    @SerializedName("entry_id")
    val id: Int,
    @SerializedName("entry_time")
    val time: String
)

