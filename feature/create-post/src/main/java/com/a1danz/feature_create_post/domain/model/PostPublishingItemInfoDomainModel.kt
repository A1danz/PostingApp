package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_places_info.domain.model.PostPlaceType

data class PostPublishingItemInfoDomainModel(
    val name: String,
    val img: String,
    val uId: String,
    val postPlaceType: PostPlaceType?
)