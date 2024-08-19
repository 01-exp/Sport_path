package com.example.maps.presentation.di

import android.content.Context
import com.example.maps.data.MapsStorage
import com.example.maps.data.MapsStorageImpl
import com.example.maps.data.retrofit.Interface.MapsRetrofitService
import com.example.maps.data.retrofit.RetrofitServiceProvider
import com.example.maps.domain.MapsRepository
import com.example.maps.domain.MapsRepositoryImpl
import com.example.maps.presentation.MapsViewModelFactory
import dagger.Module
import dagger.Provides
@Module
class MapsModule {
    @Provides
    fun provideMapsStorage(context: Context): MapsStorage = MapsStorageImpl(context)

    @Provides
    fun provideMapsViewModelFactory(
        repository: MapsRepository,
    ): MapsViewModelFactory = MapsViewModelFactory(repository)
    @Provides
    fun provideMapsRepository(
        retrofitService: MapsRetrofitService
        ,storage: MapsStorage
        ): MapsRepository = MapsRepositoryImpl(retrofitService,storage)

    @Provides
    fun provideMapsRetrofitService(): MapsRetrofitService =
        RetrofitServiceProvider.mapsRetrofitService

}