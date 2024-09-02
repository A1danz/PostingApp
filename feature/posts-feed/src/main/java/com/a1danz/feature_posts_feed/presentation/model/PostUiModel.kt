package com.a1danz.feature_posts_feed.presentation.model

import android.net.Uri
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo

data class PostUiModel(
    val id: Int,
    val text: String,
    val imgs: List<Uri>,
    val date: String,
    val postPlaces: List<PostPlaceUiInfo>
)