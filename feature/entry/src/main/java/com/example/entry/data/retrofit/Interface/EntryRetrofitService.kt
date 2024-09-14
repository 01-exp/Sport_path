package com.example.entry.data.retrofit.Interface

import com.example.entry.data.data_structures.Entry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EntryRetrofitService {

    @GET("get_entries/{id}")
    fun getUserEntries(@Path("id") userId: Int): Call<MutableList<Entry>>



    @GET("delete_entry/{entryId}")
    fun deleteEntry(@Path("entryId") entryId: Int): Call<String>
}