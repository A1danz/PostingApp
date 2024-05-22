package com.a1danz.feature_create_post.utils

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import java.util.UUID

interface PostPublishingStarter {
    fun startPublishingProcess(postPublisher: PostPublisher, postModel: PostModel)
}