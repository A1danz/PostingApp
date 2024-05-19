package com.a1danz.feature_create_post.domain.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(includes = [
    SelectedMediaModule::class,
    PostPlacesInfoModule::class,
    PostPublishingModule::class,
    UserConfigModule::class
])
class DomainModule {

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}