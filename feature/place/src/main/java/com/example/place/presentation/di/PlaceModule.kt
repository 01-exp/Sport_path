package com.example.place.presentation.di

import android.content.Context
import com.example.place.data.PlaceStorage
import com.example.place.data.PlaceStorageImpl
import com.example.place.data.retrofit.Interface.PlaceRetrofitService
import com.example.place.data.retrofit.RetrofitServiceProvider
import com.example.place.domain.PlaceRepository
import com.example.place.domain.PlaceRepositoryImpl
import com.example.place.presentation.PlaceViewModelFactory
import com.example.place.presentation.picker_manager.PickerManager
import com.example.place.presentation.picker_manager.PickerManagerImpl
import dagger.Module
import dagger.Provides
@Module
class PlaceModule {

    @Provides
    fun providePlaceViewModelFactory(
        repository: PlaceRepository,
    ): PlaceViewModelFactory = PlaceViewModelFactory(repository)
    @Provides
    fun providePlaceRepository(
       retrofitService: PlaceRetrofitService,storage: PlaceStorage
    ): PlaceRepository = PlaceRepositoryImpl(retrofitService,storage)

    @Provides
    fun providePlaceRetrofitService(): PlaceRetrofitService =
        RetrofitServiceProvider.placeRetrofitService

    @Provides
    fun providePlaceStorage(context: Context): PlaceStorage = PlaceStorageImpl(context)

    @Provides
    fun providePickerManager(context: Context):PickerManager = PickerManagerImpl(context)

}