package com.a1danz.feature_settings.presentation.model.state

import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel

sealed interface VkPlatformScreenState {
    class Linked(val userInfo: VkUserInfoUiModel) : VkPlatformScreenState
    data object Unlinked : VkPlatformScreenState
}