package com.a1danz.feature_settings.presentation.screens.social_media.vk

import androidx.lifecycle.viewModelScope
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.common.presentation.model.ReadableError
import com.a1danz.common.presentation.nav.GoBackRouter
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.mapper.VkUserGroupUiMapper
import com.a1danz.feature_settings.presentation.model.state.PlatformScreenState
import com.a1danz.feature_settings.presentation.model.state.VkPlatformScreenState
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import com.a1danz.feature_settings.presentation.screens.social_media.base.BasePlatformViewModel
import com.a1danz.feature_settings.presentation.utils.ExceptionConverter
import com.vk.id.AccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VkSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val vkSettingsRouter: GoBackRouter,
    private val resourcesManager: ResourceManager,
    private val exceptionConverter: ExceptionConverter,
    private val userGroupUiMapper: VkUserGroupUiMapper,
) : BasePlatformViewModel<VkUserInfoUiModel>() {

    private val _userGroupsState: MutableStateFlow<List<VkUserGroupUiModel>?> = MutableStateFlow(null)
    val userGroupsState: StateFlow<List<VkUserGroupUiModel>?> = _userGroupsState

    fun initScreenState() {
        userInteractor.getUserVkConfig().let { vkConfig ->
            _screenState.value = if (vkConfig != null) {
                PlatformScreenState.Linked(userInteractor.getUserVkInfoUiModel(vkConfig))
            } else {
                PlatformScreenState.Unlinked()
            }
        }
    }

    fun loadVkUserGroups() {
        viewModelScope.launch {
            runCatching {
                userInteractor.getUserGroups()
            }.onSuccess {
                _userGroupsState.value = it
            }.onFailure {
                // todo: handle ex
                _baseUiEvent.emitErrorEvent(ReadableError.Default())
            }
        }
    }

    fun handleVkGroupEdit(groupUiModel: VkUserGroupUiModel, isAdded: Boolean) {
        viewModelScope.launch {
            _userGroupsState.value = _userGroupsState.value?.let { groups ->
                groups.toMutableList().apply {
                    find { it == groupUiModel }?.let {
                        it.isChosen = isAdded
                    }
                }
            }
            if (isAdded) {
                userInteractor.addGroup(userGroupUiMapper.mapToVkGroupInfo(groupUiModel))
            } else {
                userInteractor.removeGroup(userGroupUiMapper.mapToVkGroupInfo(groupUiModel))
            }
        }
    }

    private fun unlinkVkUser() {
        viewModelScope.launch {
            runCatching {
                userInteractor.clearVkToken()
            }.onSuccess {
                vkSettingsRouter.goBack()
            }.onFailure {
                _baseUiEvent.emitErrorEvent(exceptionConverter.convertException(it))
            }
        }
    }

    fun onAccessTokenReceived(accessToken: AccessToken) {
        viewModelScope.launch {
            runCatching {
                userInteractor.saveVkToken(accessToken)
            }.onSuccess {
                _screenState.value = PlatformScreenState.Linked(
                    userInteractor.getUserVkInfoUiModel(vkConfig = it)
                )
            }.onFailure {
                _baseUiEvent.emitErrorEvent(exceptionConverter.convertException(it))
            }
        }
    }

    fun onAccessTokenFailed(description: String) {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourcesManager.getString(R.string.authorization_error),
                message = resourcesManager.getString(R.string.cant_authorize_via_vk, description),
                positiveButton = ButtonData(
                    text = resourcesManager.getString(R.string.ok)
                )
            )
        )
    }

    fun onUnlinkBtnClicked() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourcesManager.getString(R.string.are_you_sure_question),
                message = resourcesManager.getString(R.string.are_you_sure_what_you_want_unlink_account),
                positiveButton = ButtonData(resourcesManager.getString(R.string.yes), ::unlinkVkUser),
                negativeButton = ButtonData(resourcesManager.getString(R.string.no)) { }
            )
        )
    }
}