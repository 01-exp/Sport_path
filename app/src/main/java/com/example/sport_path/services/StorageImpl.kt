package com.example.sport_path.services

import android.content.Context


interface Storage{
    val defaultValue: String
    fun getUserId():Int

}


class StorageImpl(context: Context):Storage {

    override val defaultValue = "-1"
    val preferences = context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
    override fun getUserId(): Int {
        val id = preferences.getString("id",defaultValue)
        if (id == defaultValue){

        }
        return id?.toInt()!!
    }



}