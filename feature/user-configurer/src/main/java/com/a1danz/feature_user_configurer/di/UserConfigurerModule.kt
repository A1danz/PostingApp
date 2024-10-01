package com.a1danz.feature_user_configurer.di

import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.di.scope.UserConfigurerScope
import com.a1danz.feature_user_configurer.impl.UserConfigurerImpl
import com.a1danz.feature_user_configurer.repo.UserFirestoreRepository
import com.a1danz.feature_user_configurer.repo.UserRepository
import com.a1danz.feature_user_configurer.repo.impl.UserFirestoreRepositoryImpl
import com.a1danz.feature_user_configurer.repo.impl.UserRepositoryImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

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
}


@Module
interface UserRepositoryBinder {
    @Binds
    fun bindUserRepository_to_Impl(userRepo: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserFirestoreRepository_to_Impl(userFirestoreRepo: UserFirestoreRepositoryImpl): UserFirestoreRepository
}

@Module
interface UserConfigurerBinder {
    @Binds
    fun bindImpl_to_UserConfigurer(userConfigurerImpl: UserConfigurerImpl): UserConfigurer
}