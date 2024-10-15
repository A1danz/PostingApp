package com.a1danz.feature_posts_feed.data.repository

import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.core_data.database.entites.PostEntity
import com.a1danz.feature_posts_feed.data.mapper.PostEntityMapper
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.feature_posts_feed.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val dao: PostDao,
    private val postEntityMapper: PostEntityMapper
) : PostsRepository {
    override suspend fun getPosts(): List<PostDomainModel> {
        return dao.getAll().map {
            postEntityMapper.postToDomainModel(it)
        }
    }

    override suspend fun deletePostById(id: Int) {
        dao.removeById(id)
    }

    override suspend fun savePost(postDomainModel: PostDomainModel) {
        dao.insertNewPost(
            PostEntity(
                0,
                postDomainModel.text,
                postDomainModel.imgs.map { it.toString() },
                date = postDomainModel.date,
                postPlaces = postDomainModel.postPlaces
            )
        )
    }
}