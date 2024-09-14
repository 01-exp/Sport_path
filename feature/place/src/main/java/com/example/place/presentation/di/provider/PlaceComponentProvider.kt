package com.example.place.presentation.di.provider

import com.example.place.presentation.di.PlaceComponent

interface PlaceComponentProvider {
    fun getPlaceComponent(): PlaceComponent
}