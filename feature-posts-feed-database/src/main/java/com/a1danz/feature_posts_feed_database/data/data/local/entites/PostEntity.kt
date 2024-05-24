package com.a1danz.feature_posts_feed_database.data.data.local.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.Calendar

@Entity(tableName = "posts")
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val imgs: List<String>,
    val date: Calendar,
    @ColumnInfo("post_places")
    val postPlaces: List<PostPlaceEntity>
)