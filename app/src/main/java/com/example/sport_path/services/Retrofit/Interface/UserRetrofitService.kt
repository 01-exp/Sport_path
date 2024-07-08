package com.example.sport_path.services.Retrofit.Interface

import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitService {
    @GET("register/{username}/{email}/{password}")
    fun regNewUser(
        @Path("username") userName: String,
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<String>

    @GET("get_entries/{id}")
    fun getUserEntries(@Path("id") userId: Int): Call<MutableList<Entry>>


    @GET("set_entry/{userId}/{placeId}/{time}")
    fun setEntry(
        @Path("userId") userId: Int,
        @Path("placeId") placeId: Int,
        @Path("time") time: String,
    ): Call<String>

    @GET("delete_entry/{entryId}")
    fun deleteEntry(@Path("entryId") entryId: Int): Call<String>

    @GET("login/{email}/{password}")
    fun loginUser(
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<UserInfo>
}