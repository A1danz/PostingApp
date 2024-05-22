package com.a1danz.posting.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_authorization.presentation.screens.signin.SignInViewModel
import com.a1danz.feature_authorization.presentation.screens.signup.SignUpViewModel
import com.a1danz.feature_initialize.presentation.screens.initialize.InitializingViewModel
import com.a1danz.feature_settings.presentation.screens.social_media.SocialMediaSettingsViewModel
import com.a1danz.posting.presentation.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBinder::class])
class PresentationModule {
    @Provides
    @[IntoMap ViewModelKey(SignUpViewModel::class)]
    fun provideSignUpViewModel(viewModel: SignUpViewModel): ViewModel = viewModel

    @Provides
    @[IntoMap ViewModelKey(SignInViewModel::class)]
    fun provideSignInViewModel(viewModel: SignInViewModel): ViewModel = viewModel

    @Provides
    @[IntoMap ViewModelKey(MainActivityViewModel::class)]
    fun provideMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel = viewModel
}

@Module
interface ViewModelBinder {
    @Binds
    fun bindDaggerVMFactory_to_ViewModelFactory(vmFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}