package com.a1danz.feature_settings.presentation.model.tg

class TgChatsUiModel (
    val chats: List<TgChatUiModel>
)

class TgChatUiModel(
    val chatId: Long,
    val chatPhoto: String,
    val chatName: String,
    var isSelected: Boolean = false
)