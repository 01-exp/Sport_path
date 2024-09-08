package com.example.maps.presentation.di

import com.example.core.AppDeps
import com.example.profile.presentation.ConfirmDialogFragment
import com.example.profile.presentation.ProfileBottomSheetDialogFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ProfileModule::class
    ],
    dependencies = [AppDeps::class]
)
interface ProfileComponent {

    fun inject(profileBottomSheetDialogFragment: ProfileBottomSheetDialogFragment)
    fun inject(confirmDialogFragment: ConfirmDialogFragment)



    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): ProfileComponent
    }

}