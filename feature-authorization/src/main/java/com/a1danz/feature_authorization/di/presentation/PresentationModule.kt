package com.a1danz.feature_authorization.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_authorization.presentation.screens.signin.SignInViewModel
import com.a1danz.feature_authorization.presentation.screens.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [FactoryBinderModule::class])
class PresentationModule {

    @[IntoMap ViewModelKey(SignInViewModel::class)]
    @Provides
    @FeatureScope
    fun provideSignInViewModel(viewModel: SignInViewModel): ViewModel = viewModel

    @FeatureScope
    @[IntoMap ViewModelKey(SignUpViewModel::class)]
    @Provides
    fun provideSignUpViewModel(viewModel: SignUpViewModel): ViewModel = viewModel
}

@Module
interface FactoryBinderModule {
    @Binds
    fun bindFactory_to_DaggerFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}

