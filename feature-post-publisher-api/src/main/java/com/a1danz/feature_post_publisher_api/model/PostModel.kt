package com.a1danz.feature_post_publisher_api.model

data class PostModel(
    val images: List<ByteArray> = listOf(),
    val text: String
)