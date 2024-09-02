package com.a1danz.core_data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a1danz.core_data.database.converters.Converters
import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.core_data.database.entites.PostEntity


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