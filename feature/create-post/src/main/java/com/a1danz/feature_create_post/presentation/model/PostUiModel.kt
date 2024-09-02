package com.a1danz.feature_create_post.presentation.model

import java.io.Serializable

data class PostUiModel(
    val destinations: List<PostDestinationUiModel>,
    val text: String,
    val images: List<ImageUiModel> = listOf()
) : Serializable