package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model

import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo

data class PostPublishingUiModel (
    val placeUiInfo: PostPlaceUiInfo,
    val postDestinations: List<PostPublishingDestinationUiModel>
)