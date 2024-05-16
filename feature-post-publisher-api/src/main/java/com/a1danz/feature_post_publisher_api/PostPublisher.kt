package com.a1danz.feature_post_publisher_api

import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.flow.StateFlow

interface PostPublisher {
    val creatingStatusFlow: StateFlow<PostPublishingStatus?>

    fun createPost(post: PostModel)
    fun postDataIsValid(post: PostModel)
    fun getCreatingResult(): PostCreatingResult
    fun stopPublishingProcess()
}