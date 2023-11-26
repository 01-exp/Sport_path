package com.example.sport_path.services

import android.media.metrics.PlaybackSession
import android.util.Log
import android.view.View
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.User
import org.json.JSONObject
import java.net.URL
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class UsersManager {
    val mapper = jacksonObjectMapper()

    fun setNewUser(): String = URL("https://sportpath.dekked.repl.co/new-user").readText()

    fun getUser(id: Int) {
        val apiResponse = URL("https://sportpath.dekked.repl.co/get-user/$id").readText()
        if (apiResponse == "User not found") {
            throw Exception("User not found")
        }
        val id = JSONObject(apiResponse).getString("id").toInt()
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
                    "", ""
                )
                entries.add(entry)


            }
        }
        val user = User(id, entries)
        Log.d("asdfgh", user.toString())


    }


}