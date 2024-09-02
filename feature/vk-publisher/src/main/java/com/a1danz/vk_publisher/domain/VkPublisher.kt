package com.a1danz.vk_publisher.domain

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import com.a1danz.feature_vk_api.domain.VkApiRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow

class VkPublisher @AssistedInject constructor(
    @Assisted(OWNER_ID) private val ownerId: Long,
    @Assisted(IS_GROUP) private val isGroup: Boolean = false,
    private val vkApiRepository: VkApiRepository
) : PostPublisher() {
    override suspend fun createPost(post: PostModel) {
        vkApiRepository.createPost(
            ownerId = ownerId,
            message = post.text,
            photos = post.images,
            isGroup = isGroup
        )
    }

    override fun postDataIsValid(post: PostModel): Boolean {
        return post.images.isNotEmpty() || post.text.isNotBlank()
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(OWNER_ID) ownerId: Long, @Assisted(IS_GROUP) isGroup: Boolean): VkPublisher
    }

    companion object {
        const val OWNER_ID = "OWNER_ID"
        const val IS_GROUP = "IS_GROUP"
    }
}