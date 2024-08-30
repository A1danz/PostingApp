package com.a1danz.feature_posts_feed_database.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a1danz.feature_posts_feed_database.data.local.converters.Converters
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import com.a1danz.feature_posts_feed_database.data.local.entites.PostEntity

@Database(
    version = 1,
    entities = [
        PostEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}