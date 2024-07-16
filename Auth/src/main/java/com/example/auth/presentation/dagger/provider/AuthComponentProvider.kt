package com.example.auth.presentation.dagger.provider

import com.example.sport_path.services.dagger.AuthComponent

interface AuthComponentProvider {

    fun getAuthComponent():AuthComponent
}