package com.a1danz.core_data.di

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.core_data.database.di.DatabaseModule
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DatabaseModule::class
    ]
)
class DataModule {

    @ApplicationScope
    @Provides
    fun provideGson(): Gson = Gson()
}