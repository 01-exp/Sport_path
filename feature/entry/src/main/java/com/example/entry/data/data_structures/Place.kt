package com.example.entry.data.data_structures

import com.google.gson.annotations.SerializedName


data class Place(
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

