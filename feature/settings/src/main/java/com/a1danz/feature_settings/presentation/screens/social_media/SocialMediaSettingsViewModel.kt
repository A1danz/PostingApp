package com.a1danz.feature_settings.presentation.screens.social_media

import androidx.lifecycle.viewModelScope
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.mapper.TgUserInfoUiMapper
import com.a1danz.feature_settings.presentation.mapper.VkUserInfoUiMapper
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import com.a1danz.feature_settings.presentation.navigation.SocialMediaRouter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SocialMediaSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val socialMediaRouter: SocialMediaRouter,
    private val tgUserInfoUiMapper: TgUserInfoUiMapper,
    private val vkUserInfoUiMapper: VkUserInfoUiMapper,
) : BaseViewModel() {

    private val _vkUserInfoState: MutableSharedFlow<VkUserInfoUiModel?> = MutableSharedFlow(replay = 1)
    val vkUserInfoState: SharedFlow<VkUserInfoUiModel?> = _vkUserInfoState

    private val _tgUserInfoState: MutableSharedFlow<TgUserInfoUiModel?> = MutableSharedFlow(replay = 1)
    val tgUserInfoState: SharedFlow<TgUserInfoUiModel?> = _tgUserInfoState


    fun navigateToVkSettingsScreen() {
        socialMediaRouter.openVkSettings()
    }

    fun navigateToTgSettingsScreen() {
        socialMediaRouter.openTgSettings()
    }

    fun initUserInfoStates() {
        viewModelScope.launch {
            _vkUserInfoState.emit(
                userInteractor.getUserVkConfig()?.let { vkConfig ->
                    vkUserInfoUiMapper.mapToVkUserInfoUiModel(vkConfig)
                }
            )

            _tgUserInfoState.emit(
                userInteractor.getTgUserConfig()?.let { tgConfig ->
                    tgUserInfoUiMapper.mapToTgUserInfoUiModel(tgConfig)
                }
            )
        }
    }
}