package com.example.maps.data.retrofit.Interface

import retrofit2.Call
import retrofit2.http.*
import com.example.maps.data.data_structures.Place

interface MapsRetrofitService {
    @GET("getfieldbysport/{sportname}")
    fun getPlacesOnSport(@Path("sportname") sportName: String): Call<List<Place>>

}
