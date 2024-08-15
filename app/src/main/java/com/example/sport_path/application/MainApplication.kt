package com.example.sport_path.application

import android.app.Application
import android.content.Context
import com.example.core.AppDeps
import com.example.login.presentation.di.DaggerLoginComponent
import com.example.login.presentation.di.LoginComponent
import com.example.login.presentation.di.provider.LoginComponentProvider
import com.example.splash.presentation.di.DaggerSplashComponent
import com.example.splash.presentation.di.SplashComponent
import com.example.splash.presentation.di.provider.SplashComponentProvider
import com.example.sport_path.BuildConfig

import com.example.sport_path.services.dagger.AppComponent
import com.example.sport_path.services.dagger.DaggerAppComponent
import com.yandex.mapkit.MapKitFactory


class MainApplication:Application(),SplashComponentProvider, LoginComponentProvider {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appDeps(AppDepsImpl()).build()
        initializeMapKit()
    }

    private fun  initializeMapKit(){
        MapKitFactory.setApiKey(BuildConfig.API_KEY)
        MapKitFactory.initialize(this)
    }

    private inner class AppDepsImpl: AppDeps {
        override val context: Context = this@MainApplication
    }


    override fun getSplashComponent(): SplashComponent =
        DaggerSplashComponent.builder().appDeps(AppDepsImpl()).build()

    override fun getLoginComponent(): LoginComponent =
        DaggerLoginComponent.builder().appDeps(AppDepsImpl()).build()


}

val Context.appComponent : AppComponent
    get() = when(this){
        is MainApplication -> appComponent
        else ->this.applicationContext.appComponent
    }