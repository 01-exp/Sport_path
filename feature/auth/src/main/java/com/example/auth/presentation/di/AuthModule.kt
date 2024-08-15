package com.example.auth.presentation.di

import android.content.Context
import com.example.auth.data.AuthStorage
import com.example.auth.data.AuthStorageImpl
import com.example.auth.data.retrofit.Interface.AuthRetrofitService
import com.example.auth.data.retrofit.RetrofitServiceProvider
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.AuthRepositoryImpl
import com.example.auth.presentation.AuthViewModelFactory
import dagger.Module
import dagger.Provides
@Module
class AuthModule {
    @Provides
    fun provideAuthStorage(context: Context): AuthStorage = AuthStorageImpl(context)

    @Provides
    fun provideAuthViewModelFactory(
        repository: AuthRepository,
    ): AuthViewModelFactory = AuthViewModelFactory(repository)
    @Provides
    fun provideAuthRepository(
        retrofitService: AuthRetrofitService
        ,storage: AuthStorage
        ): AuthRepository = AuthRepositoryImpl(retrofitService,storage)

    @Provides
    fun provideAuthRetrofitService(): AuthRetrofitService =
        RetrofitServiceProvider.authRetrofitService

}