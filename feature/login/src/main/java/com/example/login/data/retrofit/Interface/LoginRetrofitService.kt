package com.example.login.data.retrofit.Interface

import com.example.login.data.data_structures.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginRetrofitService {

    @GET("login/{email}/{password}")
    fun loginUser(
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<UserInfo>
}