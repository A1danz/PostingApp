package com.a1danz.feature_telegram_publisher.domain

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_telegram_publisher.domain.client.ITelegramClientWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class TelegramPublisher @AssistedInject constructor(
    @Assisted(CHAT_ID) private val chatId: Long,
    private val telegramClientWrapper: ITelegramClientWrapper
) : PostPublisher() {
    override suspend fun createPost(post: PostModel) {
        telegramClientWrapper.createPost(
            chatId = chatId,
            message = post.text,
            photos = post.images
        )
    }

    override fun postDataIsValid(post: PostModel): Boolean {
        return post.text.isNotBlank() || post.images.isNotEmpty()
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(CHAT_ID) chatId: Long): TelegramPublisher
    }

    companion object {
        const val CHAT_ID = "CHAT_ID"
    }
}