package com.a1danz.feature_settings.presentation.screens.social_media.tg

import android.util.Log
import androidx.lifecycle.ViewModel
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.presentation.nav.GoBackRouter
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.model.TgChatDomainModel
import com.a1danz.feature_settings.domain.model.TgChatsDomainModel
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.a1danz.feature_settings.presentation.model.TgChatsUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TgSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val goBackRouter: GoBackRouter
) : ViewModel() {

    private val _tgUpdatesFlow: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    private val _tgConfigInitializedFlow: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val tgConfigInitializedFlow: StateFlow<Boolean?> get() = _tgConfigInitializedFlow

    fun getLinkedCode(): String {
        return userInteractor.getTgLinkedCode()
    }

    suspend fun hasUserTgConfig(): Boolean {
        return userInteractor.hasUserTgConfig()
    }

    suspend fun subscribeToLinkUpdates() {
        userInteractor.listenTgUpdates(_tgUpdatesFlow, _tgConfigInitializedFlow)
    }

    fun getTgUserInfo(): TgUserInfo {
        return userInteractor.getTgUserInfo()
    }

    suspend fun getChats(): TgChatsUiModel {
        return withContext(Dispatchers.IO) {
            userInteractor.getChats()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VIEW_MODEL", "VIEW_MODEL CLEARED")

    }

    suspend fun addSelectedChat(chatUiModel: TgChatUiModel) {
        Log.d("ADD CHAT VM", "ADDING CHAT - ${chatUiModel.chatId}")
        userInteractor.addSelectedChat(chatUiModel)
    }

    suspend fun removeSelectedChat(chatUiModel: TgChatUiModel) {
        Log.d("REMOVE CHAT VM", "REMOVING CHAT - ${chatUiModel.chatId}")
        userInteractor.removeSelectedChat(chatUiModel)
    }

    suspend fun unlinkTg() {
        userInteractor.unlinkTg()
        goBackRouter.goBack()
    }
}