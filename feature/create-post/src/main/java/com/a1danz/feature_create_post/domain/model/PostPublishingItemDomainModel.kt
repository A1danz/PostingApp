package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_post_publisher_api.PostPublisher

class PostPublishingItemDomainModel (
    val publisher: PostPublisher,
    val itemInfo: PostPublishingItemInfoDomainModel
)
