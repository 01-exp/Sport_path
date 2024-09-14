package com.example.maps.presentation.di

import android.content.Context
import com.example.profile.data.ProfileStorage

import com.example.profile.data.ProfileStorageImpl
import com.example.profile.domain.ProfileRepository
import com.example.profile.domain.ProfileRepositoryImpl
import com.example.profile.presentation.ProfileViewModelFactory
import dagger.Module
import dagger.Provides
@Module
class ProfileModule {
    @Provides
    fun provideProfileStorage(context: Context): ProfileStorage = ProfileStorageImpl(context)

    @Provides
    fun provideProfileViewModelFactory(
        repository: ProfileRepository,
    ): ProfileViewModelFactory = ProfileViewModelFactory(repository)
    @Provides
    fun provideProfileRepository(
        storage: ProfileStorage
        ): ProfileRepository = ProfileRepositoryImpl(storage)


}