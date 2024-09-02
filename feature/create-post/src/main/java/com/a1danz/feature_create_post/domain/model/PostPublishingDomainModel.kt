package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_places_info.domain.model.PostPlaceType

class PostPublishingDomainModel (
    val postPlaceType: PostPlaceType,
    val publishingItems: MutableList<PostPublishingItemDomainModel>
)