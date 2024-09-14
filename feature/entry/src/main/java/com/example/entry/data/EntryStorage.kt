package com.example.entry.data

interface EntryStorage {
    val defaultValue: String
    fun getUserId():Int
}