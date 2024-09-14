package com.example.entry.data

import android.content.Context
import android.content.SharedPreferences

class EntryStorageImpl(context: Context) : EntryStorage {
    override val defaultValue = "-1"
    private val userPreferences: SharedPreferences =
        context.getSharedPreferences("USERS_TABLE", Context.MODE_PRIVATE)

    override fun getUserId(): Int = userPreferences.getString("id", defaultValue)!!.toInt()
}