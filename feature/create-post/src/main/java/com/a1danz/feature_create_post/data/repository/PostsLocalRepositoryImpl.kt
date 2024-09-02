package com.a1danz.feature_create_post.data.repository

import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.core_data.database.entites.PostEntity
import com.a1danz.feature_create_post.domain.model.PostDomainModel
import com.a1danz.feature_create_post.domain.repository.PostsLocalRepository
import java.util.Calendar
import javax.inject.Inject

class PostsLocalRepositoryImpl @Inject constructor(
    private val postDao: PostDao
) : PostsLocalRepository {

    override suspend fun savePost(postDomainModel: PostDomainModel) {
        postDao.insertNewPost(
            PostEntity(
                text = postDomainModel.text,
                imgs = postDomainModel.images.map { it.toString() },
                date = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() },
                postPlaces = postDomainModel.places
            )
        )
    }
}