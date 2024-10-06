package com.a1danz.feature_settings.presentation.model.state

import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel

sealed interface TgPlatformScreenState {
    data class Linked(val tgUserInfoUiModel: TgUserInfoUiModel) : TgPlatformScreenState
    data object Unlinked : TgPlatformScreenState
}