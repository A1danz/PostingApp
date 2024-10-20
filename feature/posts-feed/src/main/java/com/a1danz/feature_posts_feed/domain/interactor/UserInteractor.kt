package com.a1danz.feature_posts_feed.domain.interactor

import androidx.paging.PagingData
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import kotlinx.coroutines.flow.Flow

interface UserInteractor {
    fun getPosts(): Flow<PagingData<PostUiModel>>
    suspend fun removePost(post: PostUiModel)
}
