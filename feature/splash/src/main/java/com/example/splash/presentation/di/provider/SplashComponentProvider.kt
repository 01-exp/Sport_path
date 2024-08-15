package com.example.splash.presentation.di.provider

import com.example.splash.presentation.di.SplashComponent

interface SplashComponentProvider {
    fun getSplashComponent():SplashComponent
}