package com.a1danz.feature_create_post.presentation.mapper

import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_create_post.presentation.model.PostDestinationUiModel
import com.a1danz.feature_places_info.presentation.model.getUiInfo

fun PostDestinationDomainModel.toPostDestinationUiModel(): PostDestinationUiModel {
    return PostDestinationUiModel(
        uiInfo = placeType.getUiInfo(),
        name = name,
        img = img,
    )

}