package com.a1danz.feature_create_post.domain.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class PostPlaceStaticInfo (
    val title: String,
    @DrawableRes val img: Int?,
    val placeType: PostPlaceType,
    val shortTitle: String,
    @DrawableRes val transparentIcon: Int?,
    @ColorRes val color: Int
)