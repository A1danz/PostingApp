package com.a1danz.feature_settings.presentation.screens.social_media

import androidx.lifecycle.ViewModel
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.model.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.model.VkUserInfoUiModel
import com.a1danz.feature_settings.presentation.navigation.SocialMediaRouter
import javax.inject.Inject

class SocialMediaSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val socialMediaRouter: SocialMediaRouter
) : ViewModel() {
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
        return userInteractor.getTgUserInfoUiModel(tgConfig)
    }

    fun getVkUserInfoUiModel(vkConfig: VkConfig): VkUserInfoUiModel {
        return userInteractor.getUserVkInfoUiModel(vkConfig)
    }

}