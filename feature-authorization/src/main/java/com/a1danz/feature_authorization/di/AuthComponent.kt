package com.a1danz.feature_authorization.di

import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignInViewModelFactory
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignUpViewModelFactory
import dagger.Component


@Component(modules = [AuthModule::class])
interface AuthComponent {
    fun getSignInFactory() : SignInViewModelFactory
    fun getSignUpFactory() : SignUpViewModelFactory

}