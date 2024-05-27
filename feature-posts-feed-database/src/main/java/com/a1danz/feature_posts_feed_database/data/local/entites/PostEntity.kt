package com.a1danz.feature_posts_feed_database.data.local.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import java.util.Calendar

@Entity(tableName = "posts")
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val imgs: List<String>,
    val date: Calendar,
    @ColumnInfo("post_places")
    val postPlaces: List<PostPlaceType>
)