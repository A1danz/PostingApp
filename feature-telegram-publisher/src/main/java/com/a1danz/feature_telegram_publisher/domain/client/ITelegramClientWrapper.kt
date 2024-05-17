package com.a1danz.feature_telegram_publisher.domain.client

import java.io.File


interface ITelegramClientWrapper {
    suspend fun createPost(chatId: Long, message: String, photos: List<File>)
}