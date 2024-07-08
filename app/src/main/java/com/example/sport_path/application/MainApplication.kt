package com.example.sport_path.application

import android.app.Application
import android.content.Context
import com.example.sport_path.BuildConfig

import com.example.sport_path.services.dagger.AppComponent
import com.example.sport_path.services.dagger.AppDeps
import com.example.sport_path.services.dagger.DaggerAppComponent
import com.yandex.mapkit.MapKitFactory


class MainApplication:Application() {
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

    private inner class AppDepsImpl:AppDeps{
        override val context: Context = this@MainApplication
    }
}

val Context.appComponent : AppComponent
    get() = when(this){
        is MainApplication -> appComponent
        else ->this.applicationContext.appComponent
    }