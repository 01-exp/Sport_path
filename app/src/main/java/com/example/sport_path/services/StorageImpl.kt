package com.example.sport_path.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Entry
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

    fun getUserName():String


    fun saveUserName(userName:String)

    fun userIsLogged():Boolean

    fun saveUserLogged(bool:Boolean)


    fun clearUserData()


    fun isOut():Boolean

    fun setOut(bool: Boolean)

}


class StorageImpl(context: Context):Storage {



    override val defaultValue = "-1"
    private val preferences: SharedPreferences = context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
    private val userPreferences: SharedPreferences = context.getSharedPreferences("USERS_TABLE",Context.MODE_PRIVATE)

    override fun getUserId(): Int = userPreferences.getString("id",defaultValue)!!.toInt()

    override fun getUserName(): String = userPreferences.getString("name","nickName")!!
    override fun saveUserName(userName: String) {
        val editor = userPreferences.edit()
        editor?.putString("name",userName)
        editor?.apply()
    }

    override fun userIsLogged(): Boolean = userPreferences.getBoolean("isLogged",false)

    override fun saveUserLogged(bool: Boolean) {
        val editor = userPreferences.edit()
        editor?.putBoolean("isLogged",bool)
        editor?.apply()
    }

    override fun clearUserData() {
        val editor = userPreferences.edit()
        editor?.clear()
        editor?.apply()
    }

    override fun isOut(): Boolean = preferences.getBoolean("out",false)

    override fun setOut(bool: Boolean) {
        val editor = preferences.edit()
        editor?.putBoolean("out",bool)
        editor?.apply()
    }


    override fun saveId(id: String?) {
        val editor = userPreferences.edit()
        editor?.putString("id", id.toString())
        editor?.apply()
    }

    override fun deleteData() {
        val editor = preferences.edit()
        editor?.clear()
        editor?.apply()
    }


    override fun saveCurrentSport(sportName: String){
        val editor = preferences.edit()
        Utils.Sports.forEach{
            if( it.name == sportName){
                editor?.putInt("sportId",Utils.Sports.indexOf(it))
                editor?.apply()


            }

        }
    }

    override fun getCurrentSport()= Utils.Sports[preferences.getInt("sportId",0)!!]



}