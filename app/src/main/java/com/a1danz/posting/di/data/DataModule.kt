package com.a1danz.posting.di.data

import android.content.Context
import androidx.room.Room
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_posts_feed_database.data.local.AppDatabase
import com.a1danz.feature_posts_feed_database.data.local.converters.Converters
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import com.a1danz.posting.data.repository.PostsRepositoryImpl
import com.a1danz.posting.domain.repository.PostsRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        PostsRepositoryBinderModule::class
    ]
)
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
    fun provideConverters(gson: Gson): Converters {
        return Converters(gson)
    }
}


@Module
interface PostsRepositoryBinderModule {

    @Binds
    fun bindPostsRepository_to_Impl(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}