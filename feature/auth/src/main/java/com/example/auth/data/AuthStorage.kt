package com.example.auth.data

interface AuthStorage {

    val defaultValue: String

    fun saveUserLogged(bool:Boolean)
    fun saveUserName(userName:String)

    fun saveId(id:String?)
}