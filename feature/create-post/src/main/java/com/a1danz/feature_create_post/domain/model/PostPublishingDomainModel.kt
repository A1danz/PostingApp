package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo

class PostPublishingDomainModel (
    val postPlaceStaticInfo: PostPlaceStaticInfo,
    val postPublishingItems: MutableList<PostPublishingItemDomainModel>
)