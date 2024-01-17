package com.example.sport_path.services.Retrofit

import com.example.sport_path.services.Retrofit.Interface.PlaceRetrofitService
import com.example.sport_path.services.Retrofit.Interface.UserRetrofitService

object RetrofitServiseProvider {
    val placeRetrofitService: PlaceRetrofitService by lazy {
        RetrofitClient.getClient().create(PlaceRetrofitService::class.java)
    }
    val userRetrofitService: UserRetrofitService by lazy {
        RetrofitClient.getClient().create(UserRetrofitService::class.java)
    }
}