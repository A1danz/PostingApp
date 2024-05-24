package com.a1danz.feature_posts_feed_database.data.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_places")
data class PostPlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)