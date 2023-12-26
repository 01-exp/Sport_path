package com.example.sport_path.services.maps

import android.annotation.SuppressLint
import android.util.Log
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar

class PlaceManager {

    fun getPlacesOnSport(sport: Sport):List<Place> {
        Log.d("sp",sport.toString())
        val apiResponse = URL("https://sportpath.dekked.repl.co/getfieldbysport/"+sport.nameEn).readText()
        Log.d("resp",apiResponse)
        val data = JSONObject(apiResponse).getJSONArray("data")
        val placeMutableList = mutableListOf<Place>()
        for (i in 0 until data.length()){
            val point = data.getJSONObject(i)
            val id = point.getString("id").toInt()
            val lat = point.getString("lat").toDouble()
            val lon = point.getString("lon").toDouble()
            val address = point.getString("address")
            val place = Place(
                id,
                Point(lat,lon),
                address
            )
            placeMutableList.add(place)
        }
        return placeMutableList

    }

    @SuppressLint("SimpleDateFormat")
    fun getPlaceOnline(fieldId:Int):List<Pair<String,Int>>{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date ="${day}.${month+1}.${year}"
        val apiResponse = URL("https://sportpath.dekked.repl.co/court_online/$fieldId/$date").readText()
        val data = JSONObject(apiResponse).getJSONObject("data")
        val placeOnlineList = mutableListOf<Pair<String,Int>>()
        for (timePoint in Utils.timePoints){
            val onlineCount = data.getInt(timePoint)
            placeOnlineList.add(Pair(timePoint,onlineCount))
        }
        Log.d("getPlaceonline", (date?:"почемута нул").toString())


        return placeOnlineList
    }


}