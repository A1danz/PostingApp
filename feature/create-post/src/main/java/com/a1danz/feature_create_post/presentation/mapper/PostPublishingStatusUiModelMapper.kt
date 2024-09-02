package com.a1danz.feature_create_post.presentation.mapper

import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingStatusUiModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus

fun PostPublishingStatus.getUiModel(): PostPublishingStatusUiModel {
    return when(this) {
        PostPublishingStatus.IN_PROCESS -> PostPublishingStatusUiModel.InProcess()
        PostPublishingStatus.SUCCESS -> PostPublishingStatusUiModel.Success()
        PostPublishingStatus.FAILURE -> PostPublishingStatusUiModel.Failure()
        PostPublishingStatus.RETRYING -> PostPublishingStatusUiModel.Retrying()
        PostPublishingStatus.CANCELLED -> PostPublishingStatusUiModel.Cancelled()
        PostPublishingStatus.INVALID_DATA -> PostPublishingStatusUiModel.InvalidData()
    }
}