package com.example.place.presentation.di

import com.example.core.AppDeps
import com.example.place.presentation.ModalBottomSheetFragment
import com.example.place.presentation.place_online.PlaceOnlineDialog
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        PlaceModule::class
    ],
    dependencies = [AppDeps::class]
)
interface PlaceComponent {

     fun inject(modalBottomSheetFragment: ModalBottomSheetFragment)

     fun inject(placeOnlineDialog: PlaceOnlineDialog)

    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): PlaceComponent
    }

}