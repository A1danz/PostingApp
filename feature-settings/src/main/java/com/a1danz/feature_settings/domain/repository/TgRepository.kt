package com.a1danz.feature_settings.domain.repository

import com.a1danz.feature_settings.domain.model.TgChatsDomainModel

interface TgRepository {
    suspend fun getChats(): TgChatsDomainModel
}