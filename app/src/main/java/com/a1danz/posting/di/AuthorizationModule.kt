package com.a1danz.posting.di

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.impl.AuthorizationServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AuthorizationModule {
    @ApplicationScope
    @Provides
    fun provideAuthorizationService() : AuthorizationService = AuthorizationServiceImpl()
}