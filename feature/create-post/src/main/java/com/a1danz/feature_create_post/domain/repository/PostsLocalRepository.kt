package com.a1danz.feature_create_post.domain.repository

import com.a1danz.feature_create_post.domain.model.PostDomainModel

interface PostsLocalRepository {

    suspend fun savePost(postDomainModel: PostDomainModel)
}