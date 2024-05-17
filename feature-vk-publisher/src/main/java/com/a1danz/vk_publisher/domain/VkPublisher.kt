package com.a1danz.vk_publisher.domain

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostCreatingResultType
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import com.a1danz.feature_vk_api.domain.VkApiRepository
import com.a1danz.vk_publisher.data.remote.repository.VkApiRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VkPublisher(
    private val ownerId: Long,
    private val isGroup: Boolean = false
) : PostPublisher {
    override val creatingStatusFlow: MutableStateFlow<PostPublishingStatus?> = MutableStateFlow(null)
    override var creatingResult: PostCreatingResult? = null

    @Inject lateinit var vkApiRepository: VkApiRepositoryImpl


    override suspend fun createPost(post: PostModel) {
        if (!postDataIsValid(post)) {
            creatingStatusFlow.value = PostPublishingStatus.INVALID_DATA
            return
        }
        createPost(post, 0)

    }

    override fun postDataIsValid(post: PostModel): Boolean {
        return post.images.isNotEmpty() || post.text.isNotBlank()
    }

    private suspend fun createPost(post: PostModel, attemptsCount: Int) {
        if (attemptsCount > PostPublisher.MAX_ATTEMPTS) {
            creatingStatusFlow.value = PostPublishingStatus.FAILURE
            creatingResult = PostCreatingResult(PostCreatingResultType.FAILURE)
            return
        } else if (attemptsCount == 0) {
            creatingStatusFlow.value = PostPublishingStatus.IN_PROCESS
        } else {
            creatingStatusFlow.value = PostPublishingStatus.RETRYING
        }

        runCatching {
            vkApiRepository.createPost(
                ownerId = ownerId,
                message = post.text,
                photos = post.images,
                isGroup = isGroup
            )
        }.onSuccess {
            creatingResult = PostCreatingResult(
                PostCreatingResultType.SUCCESS,

                )
            creatingStatusFlow.value = PostPublishingStatus.SUCCESS
            return
        }.onFailure {
            it.printStackTrace()
            createPost(post, attemptsCount + 1)
        }
    }
}