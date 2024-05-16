package com.a1danz.vk_page_publisher.domain

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VkPagePublisher : PostPublisher {
    override val creatingStatusFlow: MutableStateFlow<PostPublishingStatus?> = MutableStateFlow(null)

    override fun createPost(post: PostModel) {
        TODO("Not yet implemented")
    }

    override fun postDataIsValid(post: PostModel) {
        TODO("Not yet implemented")
    }

    override fun getCreatingResult(): PostCreatingResult {
        TODO("Not yet implemented")
    }

    override fun stopPublishingProcess() {
        TODO("Not yet implemented")
    }
}