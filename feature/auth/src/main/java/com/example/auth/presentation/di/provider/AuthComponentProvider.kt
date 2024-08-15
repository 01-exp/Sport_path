package com.example.auth.presentation.di.provider

import com.example.auth.presentation.di.AuthComponent


interface AuthComponentProvider {
    fun getAuthComponent(): AuthComponent
}