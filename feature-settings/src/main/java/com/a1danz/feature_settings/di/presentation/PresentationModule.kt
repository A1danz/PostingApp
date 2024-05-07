package com.a1danz.feature_settings.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.common.presentation.nav.GoBackRouter
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import com.a1danz.feature_settings.presentation.navigation.SocialMediaRouter
import com.a1danz.feature_settings.presentation.screens.social_media.SocialMediaSettingsViewModel
import com.a1danz.feature_settings.presentation.screens.social_media.tg.TgSettingsViewModel
import com.a1danz.feature_settings.presentation.screens.social_media.vk.VkSettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [
    ViewModelBinder::class,
    RouterBinder::class
])
class PresentationModule {
    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(SocialMediaSettingsViewModel::class)]
    fun provideSocialMediaSettingsViewModel(viewModel: SocialMediaSettingsViewModel): ViewModel = viewModel

    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(VkSettingsViewModel::class)]
    fun provideVkSettingsViewModel(viewModel: VkSettingsViewModel): ViewModel = viewModel

    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(TgSettingsViewModel::class)]
    fun provideTgSettingsViewModel(viewModel: TgSettingsViewModel): ViewModel = viewModel


}

@Module
interface ViewModelBinder {
    @Binds
    @FeatureScope
    fun bindDaggerVMFactory_to_ViewModelFactory(vmFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}

@Module
interface RouterBinder {
    @Binds
    fun bindSettingsRouter_to_SocialMediaRouter(settingsRouter: SettingsRouter): SocialMediaRouter

    @Binds
    fun bindSettingsRouter_to_GoBackRouter(settingsRouter: SettingsRouter): GoBackRouter
}
