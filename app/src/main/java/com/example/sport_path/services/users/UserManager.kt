package com.example.sport_path.services.users

import android.annotation.SuppressLint
import android.util.Log
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.StorageImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar


class UserManager {


    val storage = ServiceLocator.getService<Storage>("Storage")
    fun setNewUser():String {
       val apiResponse =  URL("https://sportpath.dekked.repl.co/newuser").readText()
        Log.d("fdsff",apiResponse)
        return apiResponse
    }

    fun getUserEntries():MutableList<Entry> {
        val id = storage?.getUserId()
        val apiResponse = URL("https://sportpath.dekked.repl.co/get_entries/$id").readText()
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
                    placeAdress = entryJson.getString("court_address"),
                    placeSport = entryJson.getString("court_sport"),
                    time = entryJson.getString("entry_time"),
                    id = entryJson.getInt("entry_id")
                )
                entries.add(entry)


            }
        }
        Log.d("entries",entries.toString())
        return entries
    }







    fun setEntry(placeId:Int, time:String){

        val userId = storage?.getUserId()
        val apiResponse = URL("https://sportpath.dekked.repl.co/set_entry/$userId/$placeId/$time").readText()
        Log.d("RESP",apiResponse)


    }

    fun delete_entry(entryId:Int){
        val apiResponse = URL("https://sportpath.dekked.repl.co/delete_entry/$entryId").readText()
        Log.d("RESP",apiResponse)
    }







}