package com.example.sport_path.services.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    val baseUrl = "http://5.35.92.219:222/"

    fun getClient(): Retrofit {
        val gsonBuilder = GsonBuilder().setLenient().create()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build()
        }
        return retrofit!!
    }


}