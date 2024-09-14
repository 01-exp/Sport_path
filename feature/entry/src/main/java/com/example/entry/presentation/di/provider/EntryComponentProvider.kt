package com.example.entry.presentation.di.provider

import com.example.entry.presentation.di.EntryComponent


interface EntryComponentProvider {
    fun getEntryComponent(): EntryComponent
}