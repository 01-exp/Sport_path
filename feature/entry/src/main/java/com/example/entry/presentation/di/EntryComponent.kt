package com.example.entry.presentation.di

import com.example.core.AppDeps
import com.example.entry.presentation.ConfirmDialogFragment
import com.example.entry.presentation.EntriesBottomSheetFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        EntryModule::class
    ],
    dependencies = [AppDeps::class]
)
interface EntryComponent {
    fun inject(entriesBottomSheetFragment: EntriesBottomSheetFragment)
    fun inject(confirmFragmentDialog: ConfirmDialogFragment)

    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): EntryComponent
    }

}