package com.example.auth.data.retrofit.Interface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthRetrofitService {

    @GET("register/{username}/{email}/{password}")
    fun regNewUser(
        @Path("username") userName: String,
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<String>
}