package com.a1danz.core_data.di

import android.content.Context
import androidx.room.Room
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.core_data.database.AppDatabase
import com.a1danz.core_data.database.converters.Converters
import com.a1danz.core_data.database.dao.PostDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideGson(): Gson = Gson()

    @ApplicationScope
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @ApplicationScope
    fun provideConverters(gson: Gson): Converters {
        return Converters(gson)
    }

    @ApplicationScope
    @Provides
    fun provideAppDatabase(context: Context, converters: Converters): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        )
            .addTypeConverter(converters)
            .build()
    }

    @ApplicationScope
    @Provides
    fun providePostDao(database: AppDatabase): PostDao = database.postDao()
}