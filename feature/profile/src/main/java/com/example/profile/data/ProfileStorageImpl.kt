package com.example.profile.data

import android.content.Context
import android.content.SharedPreferences

class ProfileStorageImpl(context:Context):ProfileStorage {


    override val defaultValue = "-1"
    private val userPreferences: SharedPreferences = context.getSharedPreferences("USERS_TABLE",Context.MODE_PRIVATE)

    override fun clearUserData() {
        val editor = userPreferences.edit()
        editor?.clear()
        editor?.apply()
    }

    override fun getUserName(): String = userPreferences.getString("name","nickName")!!

}