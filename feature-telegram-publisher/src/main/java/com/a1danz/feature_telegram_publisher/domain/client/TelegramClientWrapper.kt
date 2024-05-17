package com.a1danz.feature_telegram_publisher.domain.client

import com.a1danz.feature_post_publisher_api.model.PostModel
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.media.InputMedia
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.io.File
import javax.inject.Inject

class TelegramClientWrapper @Inject constructor(
    private val telegramClient: TelegramClient
) : ITelegramClientWrapper{

    override suspend fun createPost(chatId: Long, message: String, photos: List<File>) {
        if (photos.isEmpty()) {
            telegramClient.execute(SendMessage(
                chatId.toString(),
                message
            ))
        } else if (photos.size == 1) {
            telegramClient.execute(SendPhoto.builder()
                .chatId(chatId.toString())
                .caption(message)
                .photo(InputFile(photos.first()))
                .build()
            )
        } else {
            telegramClient.execute(SendMediaGroup.builder()
                .chatId(chatId.toString())
                .medias(
                    photos.mapIndexed { ind, photo ->
                        InputMediaPhoto.builder()
                            .media(photo, photo.name)
                            .caption(if (ind == 0) message else "")
                            .build()
                    }
                )
                .build()
            )
        }
    }

}