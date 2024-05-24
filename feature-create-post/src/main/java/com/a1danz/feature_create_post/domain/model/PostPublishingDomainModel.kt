package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.a1danz.feature_post_publisher_api.PostPublisher

class PostPublishingDomainModel (
    val postPlaceStaticInfo: PostPlaceStaticInfo,
    val postPublishingItems: MutableList<PostPublishingItemDomainModel>
)