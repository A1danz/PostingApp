package com.a1danz.feature_create_post.domain.model

import com.esafirm.imagepicker.model.Image

data class PostDomainModel (
    val postPlaces: List<PostPlaceType>,
    val postText: String,
    val postImages: List<Image>
)