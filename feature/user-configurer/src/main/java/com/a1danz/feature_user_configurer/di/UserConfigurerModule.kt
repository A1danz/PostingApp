package com.a1danz.feature_user_configurer.di

import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.di.scope.UserConfigurerScope
import com.a1danz.feature_user_configurer.impl.UserConfigurerImpl
import com.a1danz.feature_user_configurer.domain.repository.UserRemoteRepository
import com.a1danz.feature_user_configurer.domain.repository.UserLocalRepository
import com.a1danz.feature_user_configurer.data.repository.UserFirestoreRepositoryImpl
import com.a1danz.feature_user_configurer.data.repository.UserDatastoreRepositoryImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        UserRepositoryBinder::class,
        UserConfigurerBinder::class
    ]
)
class UserConfigurerModule {

    @UserConfigurerScope
    @Provides
    fun provideGson() = Gson()

    @UserConfigurerScope
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}


@Module
interface UserRepositoryBinder {

    @Binds
    fun bindUserRepository_to_Impl(userRepo: UserDatastoreRepositoryImpl): UserLocalRepository

    @Binds
    fun bindUserFirestoreRepository_to_Impl(userFirestoreRepo: UserFirestoreRepositoryImpl): UserRemoteRepository
}


@Module
interface UserConfigurerBinder {

    @Binds
    fun bindImpl_to_UserConfigurer(userConfigurerImpl: UserConfigurerImpl): UserConfigurer
}