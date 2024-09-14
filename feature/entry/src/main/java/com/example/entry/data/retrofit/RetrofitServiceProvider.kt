package com.example.entry.data.retrofit

import com.example.core.RetrofitClient
import com.example.entry.data.retrofit.Interface.EntryRetrofitService


object RetrofitServiceProvider {
    val entryRetrofitService: EntryRetrofitService by lazy {
        RetrofitClient.getClient().create(EntryRetrofitService::class.java)
    }

}