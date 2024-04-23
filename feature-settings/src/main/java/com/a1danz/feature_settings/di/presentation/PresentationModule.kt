package com.a1danz.feature_settings.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_settings.presentation.screens.social_media.SocialMediaSettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBinder::class])
class PresentationModule {
    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(SocialMediaSettingsViewModel::class)]
    fun provideSocialMediaSettingsViewModel(viewModel: SocialMediaSettingsViewModel): ViewModel = viewModel
}

@Module
interface ViewModelBinder {
    @Binds
    @FeatureScope
    fun bindDaggerVMFactory_to_ViewModelFactory(vmFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}
