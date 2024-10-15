package com.a1danz.feature_posts_feed.domain.di

import com.a1danz.feature_posts_feed.domain.interactor.UserInteractor
import com.a1danz.feature_posts_feed.domain.interactor.impl.UserInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        UserInteractorBinderModule::class
    ]
)
class DomainModule

@Module
interface UserInteractorBinderModule {

    @Binds
    fun bindUserInteractor_to_Impl(userInteractorImpl: UserInteractorImpl): UserInteractor
}