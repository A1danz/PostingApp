package com.a1danz.feature_posts_feed.domain.repository

import androidx.paging.PagingData
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPostsPagingFlow(): Flow<PagingData<PostDomainModel>>
    suspend fun deletePostById(id: Int)
}