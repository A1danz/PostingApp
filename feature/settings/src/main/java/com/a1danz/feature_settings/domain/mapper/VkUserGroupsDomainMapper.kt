package com.a1danz.feature_settings.domain.mapper

import android.util.Log
import com.a1danz.common.domain.model.User
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupsUiModel
import javax.inject.Inject

class VkUserGroupsDomainMapper @Inject constructor(
    private val user: User
) {
    fun mapToUiModel(domainModel: VkUserGroupsDomainModel): VkUserGroupsUiModel {
        val selectedGroupsIds = user.config.vkConfig?.userGroups?.map { it.groupId } ?: listOf()
        val selectedGroups = HashSet<Long>(selectedGroupsIds)

        Log.d("SELECTED GROUPS", selectedGroups.toString())
        val allGroups = domainModel.userGroups.map { group ->
            VkUserGroupUiModel(
                group.name,
                group.imgUrl,
                group.id
            )
        }.toMutableList()

        selectedGroups.forEach { groupId ->
            allGroups.find{ groupUi ->
                groupId == groupUi.groupId
            }?.isChosen = true
        }

        allGroups.remove(allGroups.find { it.groupId == vk_group_idL})
        allGroups.remove(allGroups.find { it.groupId == vk_group_idL})
        allGroups.remove(allGroups.find { it.groupId == vk_group_idL})
        return VkUserGroupsUiModel(allGroups)
    }
}