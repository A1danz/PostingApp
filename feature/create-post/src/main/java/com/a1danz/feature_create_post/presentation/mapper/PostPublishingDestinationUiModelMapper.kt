package com.a1danz.feature_create_post.presentation.mapper

import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingDestinationUiModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun PostDestinationDomainModel.toPostPublishingDestinationUiModel(
    coroutineScope: CoroutineScope,
    creatingStatusFlow: StateFlow<PostPublishingStatus?>
): PostPublishingDestinationUiModel {
    return PostPublishingDestinationUiModel(
        name = name,
        img = img,
        uId = uId,
        creatingStatusFlow = creatingStatusFlow.map { it?.getUiModel() }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )
    )
}