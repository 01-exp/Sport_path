package com.example.maps.data

import android.content.Context
import android.content.SharedPreferences

class MapsStorageImpl(context: Context):MapsStorage {
    override val defaultValue: String = "-1"
    private val preferences: SharedPreferences = context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)

    override fun saveCurrentSport(sportName: String){
        val editor = preferences.edit()
        Utils.Sports.forEach{
            if( it.name == sportName){
                editor?.putInt("sportId",Utils.Sports.indexOf(it))
                editor?.apply()
            }
        }
    }

    override fun getCurrentSport()= Utils.Sports[preferences.getInt("sportId",0)]

}