package com.a1danz.feature_settings.presentation.mapper

import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import javax.inject.Inject

class TgChatInfoUiMapper @Inject constructor() {
    fun toTgChatInfo(chatUiModel: TgChatUiModel): TgChatInfo {
        return chatUiModel.run {
            TgChatInfo(
                name = chatName,
                id = chatId,
                photo = chatPhoto,
            )
        }
    }

    fun toTgChatUiModel(allChats: List<TgChatInfo>, selectedChats: List<TgChatInfo>): List<TgChatUiModel> {
        val selectedHashSet = selectedChats.toHashSet()
        return allChats.map { chatInfo ->
            TgChatUiModel(
                chatId = chatInfo.id,
                chatPhoto = chatInfo.photo,
                chatName = chatInfo.name,
                isSelected = selectedHashSet.find { chatInfo.id == it.id } != null
            )
        }
    }
}