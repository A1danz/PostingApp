package com.a1danz.feature_settings.presentation.mapper

import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import javax.inject.Inject

class VkUserGroupUiMapper @Inject constructor() {
    fun mapToVkGroupInfo(userGroupUiModel: VkUserGroupUiModel): VkGroupInfo {
        return userGroupUiModel.run {
            VkGroupInfo(
                groupId = groupId,
                groupName = name,
                img = imageUrl,
            )
        }
    }

    fun mapToVkGroupUiModel(allGroups: List<VkGroupInfo>, selectedGroups: List<VkGroupInfo>): List<VkUserGroupUiModel> {
        val selectedHashSet = selectedGroups.toHashSet()
        return allGroups.map { groupInfo ->
            VkUserGroupUiModel(
                name = groupInfo.groupName,
                imageUrl = groupInfo.img,
                groupId = groupInfo.groupId,
                isChosen = selectedHashSet.find { groupInfo.groupId == it.groupId } != null
            )
        }
    }
}