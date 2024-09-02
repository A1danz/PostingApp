package com.a1danz.feature_create_post.presentation.model

import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import java.io.Serializable

data class PostDestinationUiModel(
    val uiInfo: PostPlaceUiInfo,
    val name: String,
    val img: String
) : Serializable