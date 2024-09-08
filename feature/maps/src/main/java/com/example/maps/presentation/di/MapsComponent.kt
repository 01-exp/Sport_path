package com.example.maps.presentation.di

import com.example.core.AppDeps
import com.example.maps.presentation.MapsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        MapsModule::class
    ],
    dependencies = [AppDeps::class]
)
interface MapsComponent {

    fun inject(mapsFragment: MapsFragment)



    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): MapsComponent
    }

}