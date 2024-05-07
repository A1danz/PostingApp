package com.a1danz.feature_settings.domain.interactor.tg

import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.feature_settings.domain.model.TgChatDomainModel
import com.a1danz.feature_settings.domain.model.TgChatsDomainModel
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.a1danz.feature_settings.presentation.model.TgChatsUiModel
import com.a1danz.feature_settings.presentation.model.TgUserInfoUiModel
import kotlinx.coroutines.flow.MutableStateFlow

interface TgUserInteractor {
    fun hasUserTgConfig(): Boolean
    fun getTgLinkedCode(): String
    fun getTgUserInfo(): TgUserInfo
    fun getTgUserConfig(): TgConfig?

    suspend fun getTgSelectedChats(): List<TgChatInfo>
    suspend fun saveUserTgConfig(tgUserInfo: TgUserInfo)
    suspend fun listenTgUpdates(listenFlow: MutableStateFlow<Boolean?>, tokenInitializedFlow: MutableStateFlow<Boolean?>)
    suspend fun initTgToken()
    suspend fun getChats(): TgChatsUiModel
    suspend fun addSelectedChat(chatModel: TgChatUiModel)
    suspend fun removeSelectedChat(chatModel: TgChatUiModel)
    fun getTgUserInfoUiModel(tgConfig: TgConfig): TgUserInfoUiModel
    suspend fun unlinkTg()
}