package com.a1danz.posting.di.data

import android.content.Context
import androidx.room.Room
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_posts_feed_database.data.local.AppDatabase
import com.a1danz.feature_posts_feed_database.data.local.converters.Converters
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import com.a1danz.feature_posts_feed_database.data.local.dao.PostPlaceDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideGson(): Gson = Gson()

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

    @Provides
    @ApplicationScope
    fun providePostDao(db: AppDatabase): PostDao = db.postDao()

    @Provides
    @ApplicationScope
    fun providePostPlaceDao(db: AppDatabase): PostPlaceDao = db.postPlaceDao()

    @Provides
    @ApplicationScope
    fun provideConverters(gson: Gson): Converters {
        return Converters(gson)
    }
}