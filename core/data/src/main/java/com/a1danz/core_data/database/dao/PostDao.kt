package com.a1danz.core_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.a1danz.core_data.database.entites.PostEntity

@Dao
interface PostDao {

    @Insert
    fun insertNewPost(postEntity: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAll(): List<PostEntity>

    @Query("DELETE FROM posts WHERE id=:id")
    fun removeById(id: Int)
}