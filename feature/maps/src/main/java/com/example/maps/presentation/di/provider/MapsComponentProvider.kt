package com.example.maps.presentation.di.provider

import com.example.login.presentation.di.MapsComponent

interface MapsComponentProvider {
    fun getMapsComponent(): MapsComponent
}