package com.a1danz.feature_create_post.presentation.bottom_sheet.model

import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo

data class PostPlaceUiModel(
    val staticInfo: PostPlaceStaticInfo,
    val additionalInfo: String,
    val isSelected: Boolean
)