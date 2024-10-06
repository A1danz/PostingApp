package com.a1danz.feature_settings.presentation.mapper

import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import javax.inject.Inject

class VkUserInfoUiMapper @Inject constructor() {
    fun mapToVkUserInfoUiModel(vkConfig: VkConfig): VkUserInfoUiModel {
        return vkConfig.run {
            VkUserInfoUiModel(
                name = userInfo.fullName,
                groupNames = userGroups.map { group -> group.groupName },
                photo = userInfo.userImg
            )
        }
    }
}