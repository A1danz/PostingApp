package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model

import kotlinx.coroutines.flow.StateFlow

data class PostPublishingDestinationUiModel(
    val name: String,
    val img: String,
    val uId: String,
    val creatingStatusFlow: StateFlow<PostPublishingStatusUiModel?>
)