package com.a1danz.feature_settings.presentation.screens.social_media.tg

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.a1danz.common.config.AppConfig
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.intent.IntentManager
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.common.presentation.model.ReadableError
import com.a1danz.common.presentation.model.Text
import com.a1danz.common.presentation.nav.GoBackRouter
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.mapper.TgChatInfoUiMapper
import com.a1danz.feature_settings.presentation.mapper.TgUserInfoUiMapper
import com.a1danz.feature_settings.presentation.model.event.TgSettingsUiEvent
import com.a1danz.feature_settings.presentation.model.state.PlatformScreenState
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.screens.social_media.base.BasePlatformViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TgSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val goBackRouter: GoBackRouter,
    private val tgUserInfoUiMapper: TgUserInfoUiMapper,
    private val tgChatInfoUiMapper: TgChatInfoUiMapper,
    private val resourceManager: ResourceManager,
    private val intentManager: IntentManager,
) : BasePlatformViewModel<TgUserInfoUiModel>() {

    private val _tgChatsState: MutableStateFlow<List<TgChatUiModel>?> = MutableStateFlow(null)
    val tgChatsState: StateFlow<List<TgChatUiModel>?> = _tgChatsState

    private val _uiEvent: MutableSharedFlow<TgSettingsUiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<TgSettingsUiEvent> = _uiEvent

    fun initScreenState() {
        userInteractor.getTgUserConfig().let { tgConfig ->
            _screenState.value = if (tgConfig == null) {
                PlatformScreenState.Unlinked()
            } else {
                PlatformScreenState.Linked(
                    tgUserInfoUiMapper.mapToTgUserInfoUiModel(tgConfig)
                )
            }
        }
    }

    fun loadTgChats() {
        viewModelScope.launch {
            runCatching {
                tgChatInfoUiMapper.toTgChatUiModel(
                    userInteractor.getAllChatsWithBot(),
                    userInteractor.getTgUserConfig()?.selectedChats ?: listOf()
                )
            }.onSuccess {
                _tgChatsState.value = it
            }.onFailure {
                _baseUiEvent.emitErrorEvent(ReadableError.Default())
            }
        }
    }

    fun getLinkedCode(): String {
        return userInteractor.getTgLinkedCode()
    }

    fun subscribeToLinkUpdates() {
        val tgInitializedInRemote = MutableStateFlow<Boolean?>(null)
        val unsubscriber = userInteractor.listenTgUpdates(tgInitializedInRemote)
        viewModelScope.launch {
            tgInitializedInRemote.collect {
                if (it == true) {
                    runCatching {
                        userInteractor.initTgConfig()
                    }.onSuccess { tgConfig ->
                        unsubscriber.unsubcribe()
                        _screenState.emit(
                            PlatformScreenState.Linked(tgUserInfoUiMapper.mapToTgUserInfoUiModel(tgConfig))
                        )
                        cancel()
                    }.onFailure { ex ->
                        tgInitializedInRemote.value = null
                        Log.e("Err", "Error tg config initialization", ex)
                        _baseUiEvent.emitErrorEvent(ReadableError.Custom(
                            uiMessage = Text.Resource(R.string.tg_initialization_error)
                        ))
                    }
                }
            }
        }

    }

    private fun unlinkTg() {
        viewModelScope.launch {
            userInteractor.clearTgData()
            goBackRouter.goBack()
        }
    }

    fun handleTgChatEdit(chatUiModel: TgChatUiModel, isAdded: Boolean) {
        viewModelScope.launch {
            _tgChatsState.value = _tgChatsState.value?.let { chats ->
                chats.toMutableList().apply {
                    find { it == chatUiModel }?.let {
                        it.isSelected = isAdded
                    }
                }
            }

            _tgChatsState.value?.let { tgChats ->
                userInteractor.updateSelectedChats(
                    tgChats.filter { it.isSelected }
                        .map { tgChatInfoUiMapper.toTgChatInfo(it) }
                )
            } ?: Log.e("ERR", "Try to update telegram chats, but state is null")
        }
    }

    fun onUnlinkBtnClicked() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourceManager.getString(R.string.are_you_sure_question),
                message = resourceManager.getString(R.string.are_you_sure_what_you_want_unlink_account),
                positiveButton = ButtonData(resourceManager.getString(R.string.yes), ::unlinkTg),
                negativeButton = ButtonData(resourceManager.getString(R.string.no))
            )
        )
    }

    fun onLinkedCodeBoxClicked(linkedCode: String) {
        _uiEvent.emitCopyToClipboardEvent(
            label = resourceManager.getString(R.string.posting_app_code),
            text = linkedCode
        )
    }

    fun onTelegramBotBoxClicked() {
        intentManager.getTelegramIntent(AppConfig.TG_BOT_USERNAME).let { intent ->
            if (intent == null) {
                _uiEvent.emitCopyToClipboardEvent(
                    label = resourceManager.getString(R.string.posting_bot),
                    text = AppConfig.TG_BOT_USERNAME
                )
            } else {
                _baseUiEvent.emitShowIntentEvent(intent)
            }
        }
    }

    private fun MutableSharedFlow<TgSettingsUiEvent>.emitCopyToClipboardEvent(label: String, text: String) {
        viewModelScope.launch {
            this@emitCopyToClipboardEvent.emit(
                TgSettingsUiEvent.CopyTextToClipboard(
                    label = label,
                    text = text
                )
            )
        }
    }
}