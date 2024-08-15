package com.example.splash.data

import android.content.Context
import android.content.SharedPreferences

class SplashStorageImpl(context: Context):SplashStorage {
    override val defaultValue = ""


    private val userPreferences: SharedPreferences = context.getSharedPreferences("USERS_TABLE",Context.MODE_PRIVATE)
    override val userIsLogged: Boolean
        get() = userPreferences.getBoolean("isLogged",false)



}