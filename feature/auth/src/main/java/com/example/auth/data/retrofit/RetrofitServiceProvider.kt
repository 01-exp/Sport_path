package com.example.auth.data.retrofit

import com.example.core.RetrofitClient
import com.example.auth.data.retrofit.Interface.AuthRetrofitService


object RetrofitServiceProvider {
    val authRetrofitService: AuthRetrofitService by lazy {
        RetrofitClient.getClient().create(AuthRetrofitService::class.java)
    }

}