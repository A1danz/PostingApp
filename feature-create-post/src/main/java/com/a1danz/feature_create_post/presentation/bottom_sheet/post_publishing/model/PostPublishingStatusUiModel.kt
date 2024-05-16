package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model

import com.a1danz.feature_create_post.domain.model.PostPublishingStatusDomainModel

data class PostPublishingStatusUiModel (
    val publishingStatus: PostPublishingStatusDomainModel,
    val uiName: String
)