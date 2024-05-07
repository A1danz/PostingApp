package com.a1danz.feature_settings.domain.mapper

import com.a1danz.feature_settings.domain.model.TgChatsDomainModel
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.a1danz.feature_settings.presentation.model.TgChatsUiModel
import javax.inject.Inject

class TgChatsDomainMapper @Inject constructor() {

    fun mapToUiModel(tgChatsDomainModel: TgChatsDomainModel): TgChatsUiModel {
        return TgChatsUiModel(
            chats = tgChatsDomainModel.chats.map { TgChatUiModel(
                chatId = it.chatId,
                chatPhoto = it.chatPhoto,
                chatName = it.chatName
            ) }
        )
    }
}