package com.example.login.presentation.di.provider

import com.example.login.presentation.di.LoginComponent

interface LoginComponentProvider {
    fun getLoginComponent(): LoginComponent
}