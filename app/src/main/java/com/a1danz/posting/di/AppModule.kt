package com.a1danz.posting.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.core.datastore.impl.dataStore
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.core.resources.ResourceManagerImpl
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_posts_feed.data.di.DataModule
import com.a1danz.posting.App
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module(
    includes = [
        ContextBinder::class,
        DataModule::class,
        com.a1danz.core_data.di.DataModule::class
    ]
)
class AppModule {
    @ApplicationScope
    @Provides
    fun provideResourceManager(ctx: Context): ResourceManager = ResourceManagerImpl(ctx)

    @ApplicationScope
    @Provides
    fun provideUserModelDelegate() = UserModelDelegate()

    @ApplicationScope
    @Provides
    fun provideDataStore(ctx: Context): DataStore<Preferences> = ctx.dataStore
}

@Module
interface ContextBinder {
    @ApplicationScope
    @Binds
    fun bindApplication_to_Context(application: App): Context
}