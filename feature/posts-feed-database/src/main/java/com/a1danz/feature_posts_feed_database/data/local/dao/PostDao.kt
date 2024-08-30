package com.a1danz.feature_posts_feed_database.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.a1danz.feature_posts_feed_database.data.local.entites.PostEntity

@Dao
interface PostDao {

    @Insert
    fun insertNewPost(postEntity: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAll(): List<PostEntity>

    @Query("DELETE FROM posts WHERE id=:id")
    fun removeById(id: Int)
}