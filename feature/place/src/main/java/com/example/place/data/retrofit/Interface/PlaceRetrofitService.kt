package com.example.place.data.retrofit.Interface

import retrofit2.Call
import retrofit2.http.*
import com.example.place.data.data_structures.Place

interface PlaceRetrofitService {

    @GET("/court_online/{fieldId}/{date}")
    fun getPlaceOnline(
        @Path("fieldId") fieldId: Int,
        @Path("date") date: String
    ): Call<Map<String, Int>>

    @GET("set_entry/{userId}/{placeId}/{time}")
    fun setEntry(
        @Path("userId") userId: Int,
        @Path("placeId") placeId: Int,
        @Path("time") time: String,
    ): Call<String>

}
