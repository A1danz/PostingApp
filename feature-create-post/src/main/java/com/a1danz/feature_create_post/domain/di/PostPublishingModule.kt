package com.a1danz.feature_create_post.domain.di

import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.interactor.impl.UserInteractorImpl
import dagger.Binds
import dagger.Module

@Module(includes = [
    UserInteractorBinderModule::class
])
class PostPublishingModule

@Module
interface UserInteractorBinderModule {

    @Binds
    fun bindUserInteractor_to_Impl(userInteractorImpl: UserInteractorImpl): UserInteractor
}