package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model

import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.model.PostPlaceUiModel

data class PostPublishingUiModel (
    val postPlaceUiModel: PostPlaceUiModel,
    val postPublishingStatusUiModel: PostPublishingStatusUiModel
)