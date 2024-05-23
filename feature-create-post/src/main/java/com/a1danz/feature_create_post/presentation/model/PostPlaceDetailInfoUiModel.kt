package com.a1danz.feature_create_post.presentation.model

import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo

data class PostPlaceDetailInfoUiModel(
    val staticInfo: PostPlaceStaticInfo,
    val name: String,
    val img: String
)