package com.a1danz.feature_authorization.domain.di

import com.a1danz.feature_authorization.data.service.AuthorizationServiceImpl
import com.a1danz.feature_authorization.domain.interactor.AuthorizationInteractor
import com.a1danz.feature_authorization.domain.interactor.impl.AuthorizationInteractorImpl
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        AuthorizationInteractorBinderModule::class
    ]
)
class DomainModule

@Module
interface AuthorizationInteractorBinderModule {

    @Binds
    fun bindAuthorizationInteractor_to_Impl(authorizationInteractorImpl: AuthorizationInteractorImpl): AuthorizationInteractor
}