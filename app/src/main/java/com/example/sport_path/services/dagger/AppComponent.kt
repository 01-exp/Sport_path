package com.example.sport_path.services.dagger

import android.content.Context
import com.example.core.AppDeps
import com.example.maps.presentation.di.MapsModule
import com.example.sport_path.activities.MainActivity
import com.example.sport_path.fragments.LoginFragment
import com.example.sport_path.fragments.MapFragment
import com.example.sport_path.fragments.RegistrationFragment
import com.example.sport_path.fragments.SplashFragment
import com.example.sport_path.fragments.bottomSheets.EntriesBottomSheetFragment
import com.example.sport_path.fragments.bottomSheets.ModalBottomSheetFragment
import com.example.sport_path.fragments.bottomSheets.ProfileBottomSheetDialogFragment
import com.example.sport_path.services.Storage
import com.example.sport_path.services.StorageImpl
import com.example.sport_path.services.maps.PlaceRepository
import com.example.sport_path.services.maps.PlaceRepositoryImpl
import com.example.sport_path.services.maps.PlaceViewModelFactory
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Retrofit.Interface.PlaceRetrofitService
import com.example.sport_path.services.Retrofit.Interface.UserRetrofitService
import com.example.sport_path.services.Retrofit.RetrofitServiceProvider
import com.example.sport_path.services.users.UserRepository
import com.example.sport_path.services.users.UserRepositoryImpl
import com.example.sport_path.services.users.UsersViewModelFactory

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Singleton
@Component(modules = [AppModule::class], dependencies = [AppDeps::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)
    fun inject(mapFragment: MapFragment)

    fun inject(splashFragment: SplashFragment)


    fun inject(registrationFragment: RegistrationFragment)
    fun inject(entriesBottomSheetFragment: EntriesBottomSheetFragment)
    fun inject(modalBottomSheetFragment: ModalBottomSheetFragment)
    fun inject(profileBottomSheetDialogFragment: ProfileBottomSheetDialogFragment)


    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): AppComponent
    }

}


@Module
class AppModule {
    @Provides
    fun provideStorage(context: Context): Storage = StorageImpl(context)

    @Provides
    fun providePlaceViewModelFactory(
        repository: PlaceRepository,
    ): PlaceViewModelFactory = PlaceViewModelFactory(repository)
    @Provides
    fun providePlaceRepository(
        storage: Storage,
        placeRetrofitService: PlaceRetrofitService
    ): PlaceRepository = PlaceRepositoryImpl(placeRetrofitService, storage)


    @Provides
    fun providePlaceRetrofitService(): PlaceRetrofitService =
        RetrofitServiceProvider.placeRetrofitService

    @Provides
    fun provideFragmentFactory(): FragmentFactory = FragmentFactory()


    @Provides
    fun provideUserViewModelFactory(
        repository: UserRepository
    ): UsersViewModelFactory = UsersViewModelFactory(repository)


    @Provides
    fun provideUserRepository(
        userRetrofitService: UserRetrofitService,
        storage: Storage
    ): UserRepository = UserRepositoryImpl(userRetrofitService, storage)


    @Provides
    fun provideUserRetrofitService(): UserRetrofitService =
        RetrofitServiceProvider.userRetrofitService
}