package com.example.sport_path.services.maps

import android.util.Log
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point
import org.json.JSONObject
import java.net.URL

class PlaceManager {

    fun getPlacesOnSport(sport: Sport):List<Place> {
        val apiResponse = URL("https://sportpath.dekked.repl.co/getfieldsbysport/"+sport.nameEn).readText()
        Log.d("resp",apiResponse)
        val data = JSONObject(apiResponse).getJSONArray("data")
        val placeMutableList = mutableListOf<Place>()
        for (i in 0 until data.length()){
            val point = data.getJSONObject(i)
            val lat = point.getString("lat").toDouble()
            val lon = point.getString("lon").toDouble()
            val place = Place(
                Point(lat,lon),
                "Поле"
            )
            placeMutableList.add(place)
        }
        return placeMutableList

    }


}