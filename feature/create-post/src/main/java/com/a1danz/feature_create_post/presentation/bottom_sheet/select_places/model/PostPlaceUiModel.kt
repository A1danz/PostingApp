package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.model

import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo

data class PostPlaceUiModel(
    val uiInfo: PostPlaceUiInfo,
    val additionalInfo: String,
    val isSelected: Boolean
)