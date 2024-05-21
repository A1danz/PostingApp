package com.a1danz.feature_create_post.utils

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import java.util.UUID

interface WorkerCreator {
    fun createPostPublishingWorker(postPublisher: PostPublisher, postModel: PostModel): UUID
}