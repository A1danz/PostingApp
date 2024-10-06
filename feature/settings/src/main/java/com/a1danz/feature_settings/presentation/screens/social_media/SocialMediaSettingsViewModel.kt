package com.a1danz.feature_settings.presentation.screens.social_media

import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.mapper.TgUserInfoUiMapper
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import com.a1danz.feature_settings.presentation.navigation.SocialMediaRouter
import javax.inject.Inject

class SocialMediaSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val socialMediaRouter: SocialMediaRouter,
    private val tgUserInfoUiMapper: TgUserInfoUiMapper,
) : BaseViewModel() {
    fun openVkSettingsScreen() {
        socialMediaRouter.openVkSettings()
    }

    fun openTgSettingsScreen() {
        socialMediaRouter.openTgSettings()
    }

    fun getUserTgConfig(): TgConfig? {
        return userInteractor.getTgUserConfig()
    }

    fun getUserVkConfig(): VkConfig? {
        return userInteractor.getUserVkConfig()
    }

    fun getTgUserInfoUiModel(tgConfig: TgConfig): TgUserInfoUiModel {
        return tgUserInfoUiMapper.mapToTgUserInfoUiModel(tgConfig)
    }

    fun getVkUserInfoUiModel(vkConfig: VkConfig): VkUserInfoUiModel {
        return userInteractor.getUserVkInfoUiModel(vkConfig)
    }
}