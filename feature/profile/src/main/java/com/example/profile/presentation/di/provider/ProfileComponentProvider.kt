package com.example.profile.presentation.di.provider

import com.example.maps.presentation.di.ProfileComponent

interface ProfileComponentProvider {
    fun getProfileComponent(): ProfileComponent
}