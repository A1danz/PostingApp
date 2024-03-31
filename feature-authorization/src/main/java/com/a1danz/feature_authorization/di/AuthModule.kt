package com.a1danz.feature_authorization.di

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.impl.AuthorizationServiceImpl
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignInViewModelFactory
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignUpViewModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    fun provideAuthorizationService() : AuthorizationService = AuthorizationServiceImpl()
}