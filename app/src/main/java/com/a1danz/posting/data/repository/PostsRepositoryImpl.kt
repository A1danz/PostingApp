package com.a1danz.posting.data.repository

import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import com.a1danz.feature_posts_feed_database.data.local.entites.PostEntity
import com.a1danz.posting.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postDao: PostDao
) : PostsRepository {

    override suspend fun savePost(postDomainModel: PostDomainModel) {
        postDao.insertNewPost(PostEntity(
            0,
            postDomainModel.text,
            postDomainModel.imgs.map { it.toString() },
            date = postDomainModel.date,
            postPlaces = postDomainModel.postPlaces
        ))
    }
}