package com.example.maps.data.retrofit


import com.example.core.RetrofitClient
import com.example.maps.data.retrofit.Interface.MapsRetrofitService

object RetrofitServiceProvider {
    val mapsRetrofitService: MapsRetrofitService by lazy {
        RetrofitClient.getClient().create(MapsRetrofitService::class.java)
    }
}