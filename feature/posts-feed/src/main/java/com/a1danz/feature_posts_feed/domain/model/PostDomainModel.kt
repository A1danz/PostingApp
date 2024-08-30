package com.a1danz.feature_posts_feed.domain.model

import android.net.Uri
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import java.util.Calendar

data class PostDomainModel(
    val id: Int,
    val text: String,
    val imgs: List<Uri>,
    val date: Calendar,
    val postPlaces: List<PostPlaceType>
)