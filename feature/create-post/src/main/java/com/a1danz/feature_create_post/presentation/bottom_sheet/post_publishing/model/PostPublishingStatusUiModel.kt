package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.a1danz.feature_create_post.R
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus

sealed class PostPublishingStatusUiModel (
    @StringRes val title: Int,
    @ColorRes val color: Int,
    val inProcess: Boolean
) {
    class InvalidData : PostPublishingStatusUiModel(
        title = R.string.invalid_data,
        color = com.a1danz.common.R.color.error,
        inProcess = false
    )

    class Success : PostPublishingStatusUiModel(
        title = R.string.success,
        color = com.a1danz.common.R.color.success,
        inProcess = false
    )

    class Retrying : PostPublishingStatusUiModel(
        title = R.string.retrying_attempt,
        color = com.a1danz.common.R.color.warning,
        inProcess = true
    )

    class Failure : PostPublishingStatusUiModel(
        title = R.string.publication_failed,
        color = com.a1danz.common.R.color.error,
        inProcess = false
    )

    class InProcess : PostPublishingStatusUiModel(
        title = R.string.in_process,
        color = com.a1danz.common.R.color.primary,
        inProcess = true
    )

    class Cancelled : PostPublishingStatusUiModel(
        title = R.string.publication_cancelled,
        color = com.a1danz.common.R.color.error,
        inProcess = false
    )
}