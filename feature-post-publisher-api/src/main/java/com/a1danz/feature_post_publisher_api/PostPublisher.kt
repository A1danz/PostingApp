package com.a1danz.feature_post_publisher_api

import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface PostPublisher {
    val creatingStatusFlow: MutableStateFlow<PostPublishingStatus?>

    fun getCreatingStatusFlow(): StateFlow<PostPublishingStatus?> = creatingStatusFlow
    fun createPost(post: PostModel)
    fun postDataIsValid(post: PostModel)
    fun getCreatingResult(): PostCreatingResult
    fun stopPublishingProcess()
}