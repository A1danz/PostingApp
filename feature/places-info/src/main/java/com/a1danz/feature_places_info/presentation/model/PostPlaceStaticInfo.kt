package com.a1danz.feature_places_info.presentation.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.a1danz.feature_places_info.domain.model.PostPlaceType

data class PostPlaceStaticInfo (
    val title: String,
    @DrawableRes val img: Int?,
    val placeType: PostPlaceType,
    val shortTitle: String,
    @DrawableRes val transparentIcon: Int?,
    @ColorRes val color: Int
)