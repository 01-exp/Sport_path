package com.example.sport_path.services

import android.content.Context
import android.util.Log
import com.example.sport_path.services.users.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


interface Storage{
    val defaultValue: String
    fun getUserId():Int

    fun saveId(id:String?)

    fun deleteData()

}


class StorageImpl(context: Context):Storage {

    override val defaultValue = "-1"
    val preferences = context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
    override fun getUserId(): Int {
        var id = preferences.getString("id",defaultValue)
        if (id == defaultValue){
        id = ServiceLocator.getService<UserManager>("UserManager")?.setNewUser()
        }
        Log.d("sdf",id!!)
        return id!!.toInt()
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


}