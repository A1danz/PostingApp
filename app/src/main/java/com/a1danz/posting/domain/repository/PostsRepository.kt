package com.a1danz.posting.domain.repository

import com.a1danz.feature_posts_feed.domain.model.PostDomainModel

interface PostsRepository {

    suspend fun savePost(postDomainModel: PostDomainModel)
}