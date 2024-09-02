package com.a1danz.feature_create_post.presentation.mapper

import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingUiModel
import com.a1danz.feature_places_info.presentation.model.getUiInfo
import kotlinx.coroutines.CoroutineScope

fun PostPublishingDomainModel.toPostPublishingDestinationUiModel(
    coroutineScope: CoroutineScope
): PostPublishingUiModel {
    return PostPublishingUiModel(
        placeUiInfo = postPlaceType.getUiInfo(),
        postDestinations = this.publishingItems.map {
            it.destinationInfo.toPostPublishingDestinationUiModel(coroutineScope, it.publisher.creatingStatusFlow)
        }
    )
}