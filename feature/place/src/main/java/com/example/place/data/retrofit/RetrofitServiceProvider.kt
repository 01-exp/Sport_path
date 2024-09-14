package com.example.place.data.retrofit


import com.example.core.RetrofitClient
import com.example.place.data.retrofit.Interface.PlaceRetrofitService

object RetrofitServiceProvider {
    val placeRetrofitService: PlaceRetrofitService by lazy {
        RetrofitClient.getClient().create(PlaceRetrofitService::class.java)
    }
}