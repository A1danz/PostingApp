package com.a1danz.feature_posts_feed.data.repository

import com.a1danz.feature_posts_feed.data.mapper.PostModelDomainMapper
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.feature_posts_feed.domain.repository.PostsRepository
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val dao: PostDao,
    private val postModelDomainMapper: PostModelDomainMapper
) : PostsRepository {
    override suspend fun getPosts(): List<PostDomainModel> {
        return dao.getAll().map {
            postModelDomainMapper.postToDomainModel(it)
        }
    }

    override suspend fun deletePostById(id: Int) {
        dao.removeById(id)
    }
}