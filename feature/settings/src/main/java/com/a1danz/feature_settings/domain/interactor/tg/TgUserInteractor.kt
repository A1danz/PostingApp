package com.a1danz.feature_settings.domain.interactor.tg

import com.a1danz.common.utils.Unsubscriber
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import kotlinx.coroutines.flow.MutableStateFlow

interface TgUserInteractor {
    fun getTgLinkedCode(): String
    fun getTgUserConfig(): TgConfig?
    fun listenTgUpdates(tgInitializedInRemote: MutableStateFlow<Boolean?>): Unsubscriber
    suspend fun initTgConfig(): TgConfig
    suspend fun getAllChatsWithBot(): List<TgChatInfo>
    suspend fun updateSelectedChats(chats: List<TgChatInfo>)
    suspend fun clearTgData()
}