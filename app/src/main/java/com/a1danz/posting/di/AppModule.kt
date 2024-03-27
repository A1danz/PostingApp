package com.a1danz.posting.di

import android.content.Context
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.posting.App
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface AppModule {
    @ApplicationScope
    @Binds
    fun provideContext(application: App): Context
}