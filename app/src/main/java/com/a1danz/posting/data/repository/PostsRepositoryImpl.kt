package com.a1danz.posting.data.repository

import androidx.room.Dao
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import com.a1danz.posting.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
) : PostsRepository {

    override suspend fun savePost(postDomainModel: PostDomainModel) {
        postDao.insertNewPost()
    }
}