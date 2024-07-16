package com.example.auth.data.Retrofit.Interface

import com.example.sport_path.data_structures.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitService {
    @GET("register/{username}/{email}/{password}")
    fun regNewUser(
        @Path("username") userName: String,
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<String>

    @GET("login/{email}/{password}")
    fun loginUser(
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<UserInfo>
}