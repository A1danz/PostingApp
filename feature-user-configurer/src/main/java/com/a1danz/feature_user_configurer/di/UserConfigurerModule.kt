package com.a1danz.feature_user_configurer.di

import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.impl.UserConfigurerImpl
import com.a1danz.feature_user_configurer.repo.UserRepository
import com.a1danz.feature_user_configurer.repo.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        UserRepositoryBinder::class,
        UserConfigurerBinder::class
    ]
)
class UserConfigurerModule


@Module
interface UserRepositoryBinder {
    @Binds
    fun bindUserRepository_to_Impl(userRepo: UserRepositoryImpl): UserRepository
}

@Module
interface UserConfigurerBinder {
    @Binds
    fun bindImpl_to_UserConfigurer(userConfigurerImpl: UserConfigurerImpl): UserConfigurer
}