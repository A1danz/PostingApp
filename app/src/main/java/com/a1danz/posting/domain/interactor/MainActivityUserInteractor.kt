package com.a1danz.posting.domain.interactor

import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel

interface MainActivityUserInteractor {
    suspend fun startPublishingProcess(postPublisher: PostPublisher, postModel: PostModel)
    suspend fun savePostToFeed(items: List<PostPublishingItemDomainModel>)
}