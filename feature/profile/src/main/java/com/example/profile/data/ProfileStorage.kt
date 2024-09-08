package com.example.profile.data

interface ProfileStorage {
    val defaultValue: String
    fun clearUserData()
    fun getUserName(): String

}