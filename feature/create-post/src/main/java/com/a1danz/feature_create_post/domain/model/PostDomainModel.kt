package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_places_info.domain.model.PostPlaceType
import java.io.File

data class PostDomainModel (
    val places: List<PostPlaceType>,
    val text: String,
    val images: List<File>
)