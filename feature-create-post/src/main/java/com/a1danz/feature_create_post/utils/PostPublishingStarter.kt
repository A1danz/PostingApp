package com.a1danz.feature_create_post.utils

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import java.util.UUID

interface PostPublishingStarter {
    fun startPublishingProcess(uId: String, postPublisher: PostPublisher, postModel: PostModel)
    fun getPublishersMap(): HashMap<String, PostPublisher>
    fun publishingInProcess(): Boolean
}