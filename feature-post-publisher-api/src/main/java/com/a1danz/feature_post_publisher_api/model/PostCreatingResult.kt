package com.a1danz.feature_post_publisher_api.model

data class PostCreatingResult (
    val resultType: PostCreatingResultType,
    var resultLink: String? = null
)

enum class PostCreatingResultType {
    SUCCESS, FAILURE
}