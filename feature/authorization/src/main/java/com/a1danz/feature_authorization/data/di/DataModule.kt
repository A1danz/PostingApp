package com.a1danz.feature_authorization.data.di

import com.a1danz.feature_authorization.data.service.AuthorizationServiceImpl
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        ServiceBinderModule::class,
    ]
)
class DataModule {

    @Provides
    fun provideDispatcher() = Dispatchers.IO
}


@Module
interface ServiceBinderModule {

    @Binds
    fun bindAuthorizationService_to_Impl(authorizationServiceImpl: AuthorizationServiceImpl) : AuthorizationService
}