package com.a1danz.feature_post_publisher_api.model

import java.io.File

data class PostModel(
    val images: List<File> = listOf(),
    val text: String
)