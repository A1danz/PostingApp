package com.a1danz.feature_posts_feed_database.data.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.a1danz.feature_posts_feed_database.data.data.local.entites.PostPlaceEntity

@Dao
interface PostPlaceDao {

    @Query("SELECT * FROM post_places WHERE id=:id")
    fun findPostPlaceById(id: Int): PostPlaceEntity?

    @Insert
    fun insertPostPlace(postPlaceEntity: PostPlaceEntity)

    @Query("SELECT * FROM post_places WHERE id=:name")
    fun findPostPlaceByName(name: String): PostPlaceEntity?
}