package com.a1danz.feature_post_publisher_api

import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface PostPublisher {
    val creatingStatusFlow: MutableStateFlow<PostPublishingStatus?>
    var creatingResult: PostCreatingResult?

    fun getCreatingStatusFlow(): StateFlow<PostPublishingStatus?> = creatingStatusFlow
    suspend fun createPost(post: PostModel)
    fun postDataIsValid(post: PostModel): Boolean

    companion object {
        const val MAX_ATTEMPTS = 3
    }
}