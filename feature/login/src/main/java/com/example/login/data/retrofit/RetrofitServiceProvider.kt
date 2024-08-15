package com.example.login.data.retrofit

import com.example.core.RetrofitClient
import com.example.login.data.retrofit.Interface.LoginRetrofitService


object RetrofitServiceProvider {
    val loginRetrofitService: LoginRetrofitService by lazy {
        RetrofitClient.getClient().create(LoginRetrofitService::class.java)
    }

}