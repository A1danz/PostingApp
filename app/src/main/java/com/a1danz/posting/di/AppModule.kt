package com.a1danz.posting.di

import android.content.Context
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.core.resources.ResourceManagerImpl
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.posting.App
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module(includes = [ContextBinder::class])
class AppModule {
    @ApplicationScope
    @Provides
    fun provideResourceManager(ctx : Context) : ResourceManager = ResourceManagerImpl(ctx)
}

@Module
interface ContextBinder {
    @ApplicationScope
    @Binds
    fun provideContext(application: App): Context
}