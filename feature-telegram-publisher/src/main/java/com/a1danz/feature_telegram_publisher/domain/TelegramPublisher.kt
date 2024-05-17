package com.a1danz.feature_telegram_publisher.domain

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostCreatingResultType
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import com.a1danz.feature_telegram_publisher.di.DaggerTelegramPublisherComponent
import com.a1danz.feature_telegram_publisher.domain.client.ITelegramClientWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import java.io.File
import javax.inject.Inject

class TelegramPublisher(
    private val chatId: Long
) : PostPublisher {
    override val creatingStatusFlow: MutableStateFlow<PostPublishingStatus?> =
        MutableStateFlow(null)
    override var creatingResult: PostCreatingResult? = null

    @Inject
    lateinit var telegramClientWrapper: ITelegramClientWrapper

    override suspend fun createPost(post: PostModel) {
        if (!postDataIsValid(post)) {
            creatingStatusFlow.value = PostPublishingStatus.INVALID_DATA
            return
        }
        createPost(post, 0)
    }

    override fun postDataIsValid(post: PostModel): Boolean {
        return post.text.isNotBlank() || post.images.isNotEmpty()
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
            telegramClientWrapper.createPost(
                chatId = chatId,
                message = post.text,
                photos = post.images
            )
        }.onSuccess {
            creatingStatusFlow.value = PostPublishingStatus.SUCCESS
            creatingResult = PostCreatingResult(PostCreatingResultType.SUCCESS)
        }.onFailure {
            createPost(post, attemptsCount + 1)
        }
    }
}