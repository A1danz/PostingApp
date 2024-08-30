package com.a1danz.feature_settings.domain.model

data class TgChatsDomainModel(
    val chats: List<TgChatDomainModel>
)

data class TgChatDomainModel(
    val chatId: Long,
    val chatName: String,
    val chatPhoto: String
)
