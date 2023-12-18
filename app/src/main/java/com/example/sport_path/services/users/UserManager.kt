package com.example.sport_path.services.users

import android.annotation.SuppressLint
import android.util.Log
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar


class UserManager {



    fun setNewUser():String {

       val apiResponse =  URL("https://sportpath.dekked.repl.co/newuser").readText()
        Log.d("fdsff",apiResponse)
        return apiResponse
    }

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
//                val entry = Entry(
//                    Place(
//                        Point(
//                            pointJson.getString("lat").toDouble(),
//                            pointJson.getString("lon").toDouble()
//                        ),
//                        "Поле"
//                    ),
//                    entryJson.getString("time")
//                )
//                entries.add(entry)


            }
        }
        return entries
    }







    fun setEntry(placeId:Int, time:String){

        val userId = ServiceLocator.getService<Storage>("Storage")?.getUserId()
        val apiResponse = URL("https://sportpath.dekked.repl.co/set_entry/$userId/$placeId/$time").readText()
        Log.d("RESP",apiResponse)


    }







}