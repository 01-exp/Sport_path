package com.example.sport_path.services.users

import android.util.Log
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import org.json.JSONObject
import java.net.URL


class UserManager {


    val storage = ServiceLocator.getService<Storage>("Storage")!!
    fun setNewUser():String {
       val apiResponse =  URL("http://5.35.92.219:92/newuser").readText()
        Log.d("mlog",apiResponse)
        return apiResponse
    }

    fun getUserEntries():MutableList<Entry> {
        val id = storage.getUserId()
        val apiResponse = URL("http://5.35.92.219:92/get_entries/$id").readText()
        if (apiResponse == "User not found") {
            throw Exception("User not found")
        }
        val entriesJson = JSONObject(apiResponse).getJSONArray("data")
        val entries = mutableListOf<Entry>()
        if (entriesJson.length() > 0) {
            for (i in 0 until entriesJson.length()) {
                val entryJson = entriesJson.getJSONObject(i)
                val entry = Entry(
                    placeId = entryJson.getInt("court_id"),
                    placeAddress = entryJson.getString("court_address"),
                    placeSport = entryJson.getString("court_sport"),
                    time  = entryJson.getString("entry_time"),
                    id = entryJson.getInt("entry_id")
                )
                entries.add(entry)


            }
        }
        Log.d("mlog",entries.toString())
        return entries
    }







    fun setEntry(placeId:Int, time:String){

        val userId = storage.getUserId()
        val apiResponse = URL("https://3ce546dc-4fc1-4642-a255-0f8b8d409fa6-00-14j51wv7f0rgt.riker.replit.dev/set_entry/$userId/$placeId/$time").readText()
        Log.d("mlogEn",apiResponse)


    }

    fun delete_entry(entryId:Int){
        val apiResponse = URL("https://3ce546dc-4fc1-4642-a255-0f8b8d409fa6-00-14j51wv7f0rgt.riker.replit.dev/delete_entry/$entryId").readText()
        Log.d("mlog",apiResponse)
    }







}