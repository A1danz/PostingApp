package com.a1danz.feature_settings.domain.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}