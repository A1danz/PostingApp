package com.a1danz.feature_authorization.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.feature_authorization.di.scope.AuthorizationScope
import com.a1danz.feature_authorization.presentation.screens.signin.SignInViewModel
import com.a1danz.feature_authorization.presentation.screens.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [FactoryBinderModule::class])
class PresentationModule {

    @AuthorizationScope
    @[IntoMap ViewModelKey(SignInViewModel::class)]
    @Provides
    fun provideSignInViewModel(viewModel: SignInViewModel): ViewModel = viewModel

    @AuthorizationScope
    @[IntoMap ViewModelKey(SignUpViewModel::class)]
    @Provides
    fun provideSignUpViewModel(viewModel: SignUpViewModel): ViewModel = viewModel
}

@Module
interface FactoryBinderModule {

    @Binds
    fun bindFactory_to_DaggerFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}

