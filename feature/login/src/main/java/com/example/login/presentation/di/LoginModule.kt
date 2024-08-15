package com.example.login.presentation.di

import android.content.Context
import com.example.login.data.LoginStorage
import com.example.login.data.LoginStorageImpl
import com.example.login.data.retrofit.Interface.LoginRetrofitService
import com.example.login.data.retrofit.RetrofitServiceProvider
import com.example.login.domain.LoginRepository
import com.example.login.domain.LoginRepositoryImpl
import com.example.login.presentation.LoginViewModelFactory
import dagger.Module
import dagger.Provides
@Module
class LoginModule {
    @Provides
    fun provideLoginStorage(context: Context): LoginStorage = LoginStorageImpl(context)

    @Provides
    fun provideSplashViewModelFactory(
        repository: LoginRepository,
    ): LoginViewModelFactory = LoginViewModelFactory(repository)
    @Provides
    fun provideSplashRepository(
        retrofitService: LoginRetrofitService
        ,storage: LoginStorage
        ): LoginRepository = LoginRepositoryImpl(retrofitService,storage)

    @Provides
    fun provideLoginRetrofitService(): LoginRetrofitService =
        RetrofitServiceProvider.loginRetrofitService

}