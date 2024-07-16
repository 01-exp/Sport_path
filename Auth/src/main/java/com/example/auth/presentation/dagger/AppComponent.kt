package com.example.sport_path.services.dagger

import android.content.Context
import com.example.auth.data.Storage
import com.example.auth.data.StorageImpl
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.AuthRepositoryImpl
import com.example.auth.presentation.AuthViewModelFactory
import com.example.auth.presentation.FragmentFactory
import com.example.auth.presentation.fragments.LoginFragment
import com.example.auth.presentation.fragments.RegistrationFragment
import com.example.auth.data.Retrofit.Interface.AuthRetrofitService
import com.example.auth.data.Retrofit.RetrofitClient


import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

interface AppDeps {
    val context: Context
}

@Singleton
@Component(modules = [AuthModule::class], dependencies = [AppDeps::class])
interface AuthComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(registrationFragment: RegistrationFragment)


    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): AuthComponent
    }

}


@Module
class AuthModule {
    @Provides
    fun provideStorage(context: Context): Storage = StorageImpl(context)



    @Provides
    fun provideFragmentFactory(): FragmentFactory = FragmentFactory()


    @Provides
    fun provideAuthViewModelFactory(
        repository: AuthRepository
    ): AuthViewModelFactory = AuthViewModelFactory(repository)


    @Provides
    fun provideAuthRepository(
        authRetrofitService: AuthRetrofitService,
        storage: Storage
    ): AuthRepository = AuthRepositoryImpl(authRetrofitService, storage)


    @Provides
    fun provideAuthRetrofitService(): AuthRetrofitService =
        RetrofitClient.getClient().create(AuthRetrofitService::class.java)
}