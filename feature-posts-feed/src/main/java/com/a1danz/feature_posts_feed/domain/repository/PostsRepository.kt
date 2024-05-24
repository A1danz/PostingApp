package com.a1danz.feature_posts_feed.domain.repository

import com.a1danz.feature_posts_feed.domain.model.PostDomainModel

interface PostsRepository {

    suspend fun getPosts(): List<PostDomainModel>
}