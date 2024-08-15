package com.example.auth.presentation.di

import com.example.auth.presentation.AuthFragment
import com.example.core.AppDeps
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AuthModule::class
    ],
    dependencies = [AppDeps::class]
)
interface AuthComponent {

    fun inject(authFragment: AuthFragment)

    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): AuthComponent
    }

}