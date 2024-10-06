package com.a1danz.feature_settings.domain.repository

import com.a1danz.common.domain.model.TgChatInfo

interface TgRepository {
    suspend fun getChats(userId: String): List<TgChatInfo>
}