package com.a1danz.common.di.presentation

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelBinder {
    @Binds
    fun bindDaggerVMFactory_to_ViewModelFactory(vmFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}