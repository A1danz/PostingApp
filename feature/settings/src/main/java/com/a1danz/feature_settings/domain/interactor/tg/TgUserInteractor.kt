package com.a1danz.feature_settings.domain.interactor.tg

import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import kotlinx.coroutines.flow.MutableStateFlow

interface TgUserInteractor {
    fun getTgLinkedCode(): String
    fun getTgUserInfo(): TgUserInfo
    fun getTgUserConfig(): TgConfig?
    fun getTgSelectedChats(): List<TgChatInfo>
    fun listenTgUpdates(tgInitializedInRemote: MutableStateFlow<Boolean?>): Unsubscriber
    suspend fun saveUserTgConfig(tgUserInfo: TgUserInfo)
    suspend fun initTgConfig(): TgConfig
    suspend fun getChats(): List<TgChatUiModel>
    suspend fun addSelectedChat(chatModel: TgChatUiModel)
    suspend fun removeSelectedChat(chatModel: TgChatUiModel)
    suspend fun unlinkTg()
}