package com.example.entry.presentation.di

import android.content.Context
import com.example.entry.data.EntryStorage
import com.example.entry.data.EntryStorageImpl
import com.example.entry.data.retrofit.Interface.EntryRetrofitService
import com.example.entry.data.retrofit.RetrofitServiceProvider
import com.example.entry.domain.EntryRepository
import com.example.entry.domain.EntryRepositoryImpl
import com.example.entry.presentation.EntryViewModelFactory
import dagger.Module
import dagger.Provides
@Module
class EntryModule {
    @Provides
    fun provideEntryStorage(context: Context): EntryStorage = EntryStorageImpl(context)

    @Provides
    fun provideEntryViewModelFactory(
        repository: EntryRepository,
    ): EntryViewModelFactory = EntryViewModelFactory(repository)
    @Provides
    fun provideEntryRepository(
        retrofitService: EntryRetrofitService
        ,storage: EntryStorage
        ): EntryRepository = EntryRepositoryImpl(retrofitService,storage)

    @Provides
    fun provideEntryRetrofitService(): EntryRetrofitService =
        RetrofitServiceProvider.entryRetrofitService

}