package com.a1danz.feature_posts_feed.domain.interactor

import com.a1danz.feature_posts_feed.presentation.model.PostUiModel

interface UserInteractor {
    suspend fun getPosts(): List<PostUiModel>
    suspend fun removePost(post: PostUiModel)
}
