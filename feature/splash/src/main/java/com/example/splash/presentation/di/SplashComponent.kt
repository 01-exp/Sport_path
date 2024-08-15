package com.example.splash.presentation.di

import android.content.Context
import com.example.core.AppDeps
import com.example.splash.presentation.SplashFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        SplashModule::class
    ],
    dependencies = [AppDeps::class]
)
interface SplashComponent {

    fun inject(splashFragment: SplashFragment)

    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): SplashComponent
    }

}