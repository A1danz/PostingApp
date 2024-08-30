package com.a1danz.feature_initialize.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.feature_initialize.presentation.screens.initialize.InitializingViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [FactoryBinderModule::class])
class PresentationModule {

    @Provides
    @[IntoMap ViewModelKey(InitializingViewModel::class)]
    fun provideLoadingViewModel(viewModel: InitializingViewModel): ViewModel = viewModel
}

@Module
interface FactoryBinderModule {
    @Binds
    fun bindFactory_to_DaggerFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}