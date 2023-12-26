package com.example.sport_path.services

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.services.users.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


interface Storage{
    val defaultValue: String
    fun getUserId():Int

    fun saveId(id:String?)

    fun deleteData()

    fun saveCurrentSport(sportName: String)

    fun getCurrentSport():Sport

}


class StorageImpl(context: Context):Storage {

    override val defaultValue = "-1"
    val preferences = context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
    @SuppressLint("SuspiciousIndentation")
    override fun getUserId(): Int {
        var id = preferences.getString("id",defaultValue)
        if (id == defaultValue){
        id = ServiceLocator.getService<UserManager>("UserManager")?.setNewUser()
            saveId(id)
            Log.d("fsd","я на сервер обращаюсь")
        }

        Log.d("ФФФФФФФФsdf",id!!)
        return id.toInt()
    }

    override fun saveId(id: String?) {
        val editor = preferences?.edit()
        editor?.putString("id", id.toString())
        editor?.apply()
    }

    override fun deleteData() {
        val editor = preferences?.edit()
        editor?.clear()
        editor?.apply()
    }


    override fun saveCurrentSport(sportName: String){
        val editor = preferences?.edit()
        Utils.Sports.forEach{
            if( it.name == sportName){
                editor?.putInt("sportId",Utils.Sports.indexOf(it))
                editor?.apply()


            }

        }
    }

    override fun getCurrentSport()= Utils.Sports[preferences?.getInt("sportId",0)!!]



}