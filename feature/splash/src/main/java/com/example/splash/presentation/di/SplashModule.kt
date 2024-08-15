package com.example.splash.presentation.di

import android.content.Context
import com.example.splash.data.SplashStorage
import com.example.splash.data.SplashStorageImpl
import com.example.splash.domain.SplashRepository
import com.example.splash.domain.SplashRepositoryImpl
import com.example.splash.presentation.SplashViewModelFactory

import dagger.Module
import dagger.Provides


@Module
class SplashModule {

    @Provides
    fun provideSplashStorage(context: Context): SplashStorage = SplashStorageImpl(context)

    @Provides
    fun provideSplashViewModelFactory(
        repository: SplashRepository,
    ): SplashViewModelFactory = SplashViewModelFactory(repository)
    @Provides
    fun provideSplashRepository(
        storage: SplashStorage,

        ): SplashRepository = SplashRepositoryImpl(storage)

}