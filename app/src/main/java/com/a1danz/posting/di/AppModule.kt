package com.a1danz.posting.di

import android.content.Context
import com.a1danz.common.core.datastore.DataStoreManager
import com.a1danz.common.core.datastore.impl.DataStoreManagerImpl
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.core.resources.ResourceManagerImpl
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.posting.App
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module(includes = [ContextBinder::class])
class AppModule {
    @ApplicationScope
    @Provides
    fun provideResourceManager(ctx : Context) : ResourceManager = ResourceManagerImpl(ctx)

    @ApplicationScope
    @Provides
    fun provideUserModelDelegate() = UserModelDelegate()

    @ApplicationScope
    @Provides
    fun provideDataStoreManager(ctx: Context): DataStoreManager = DataStoreManagerImpl(ctx)
}

@Module
interface ContextBinder {
    @ApplicationScope
    @Binds
    fun bindApplication_to_Context(application: App): Context
}