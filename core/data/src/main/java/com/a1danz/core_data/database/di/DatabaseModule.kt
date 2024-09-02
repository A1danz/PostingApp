package com.a1danz.core_data.database.di

import android.content.Context
import androidx.room.Room
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.core_data.database.AppDatabase
import com.a1danz.core_data.database.converters.Converters
import com.a1danz.core_data.database.dao.PostDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
internal class DatabaseModule {

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