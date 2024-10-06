package com.a1danz.feature_settings.presentation.model.tg

data class TgChatUiModel(
    val chatId: Long,
    val chatPhoto: String,
    val chatName: String,
    var isSelected: Boolean = false
)