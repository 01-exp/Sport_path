package com.example.sport_path.services

import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.User
import org.json.JSONObject
import java.net.URL
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yandex.mapkit.geometry.Point


class UsersManager {



    fun setNewUser(): String = URL("https://sportpath.dekked.repl.co/new-user").readText()

    fun getUserEntries(id: Int):List<Entry> {
        val apiResponse = URL("https://sportpath.dekked.repl.co/get-user/$id").readText()
        if (apiResponse == "User not found") {
            throw Exception("User not found")
        }
        val entriesJson = JSONObject(apiResponse).getJSONArray("entries")
        val entries = mutableListOf<Entry>()
        if (entriesJson.length() > 0) {
            for (i in 0 until entriesJson.length()) {
                val entryJson = entriesJson.getJSONObject(i)
                val placeJson = entryJson.getJSONObject("place")
                val pointJson = placeJson.getJSONObject("point")
                val entry = Entry(
                    Place(
                        Point(
                            pointJson.getString("lat").toDouble(),
                            pointJson.getString("lon").toDouble()
                        ),
                        "Поле"
                    ),
                    entryJson.getString("time")
                )
                entries.add(entry)


            }
        }
        return entries
    }



    fun setUserEntries(id:Int){

    }




}