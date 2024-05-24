package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model

import androidx.annotation.ColorRes
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus

data class PostPublishingStatusUiModel (
    val publishingStatus: PostPublishingStatus,
    val uiName: String,
    @ColorRes val color: Int
)