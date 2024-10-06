package com.a1danz.posting.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.core.datastore.impl.dataStore
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.core.resources.ResourceManagerImpl
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.intent.IntentManager
import com.a1danz.common.intent.impl.IntentManagerImpl
import com.a1danz.posting.App
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module(
    includes = [
        DependenciesBinderModule::class,
        com.a1danz.core_data.di.DataModule::class,
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
interface DependenciesBinderModule {
    @Binds
    fun bindApplication_to_Context(application: App): Context

    @Binds
    fun bindsIntentManager_to_Impl(intentManagerImpl: IntentManagerImpl): IntentManager

}