package com.example.sport_path.services.Retrofit.Interface

import retrofit2.Call
import retrofit2.http.*
import com.example.sport_path.data_structures.Place

interface PlaceRetrofitService {
    @GET("getfieldbysport/{sportname}")
    fun getPlacesOnSport(@Path("sportname") sportName: String): Call<List<Place>>

    @GET("/court_online/{fieldId}/{date}")
    fun getPlaceOnline(
        @Path("fieldId") fieldId: Int,
        @Path("date") date: String
    ): Call<Map<String, Int>>
}
