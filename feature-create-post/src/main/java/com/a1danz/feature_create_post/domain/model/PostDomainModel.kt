package com.a1danz.feature_create_post.domain.model

import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.esafirm.imagepicker.model.Image
import java.io.File

data class PostDomainModel (
    val postPlaces: List<PostPlaceType>,
    val postText: String,
    val postImages: List<File>
)