package com.a1danz.feature_settings.data.mapper

import com.a1danz.feature_settings.data.remote.vk.pojo.UserGroupsResponse
import com.a1danz.feature_settings.domain.model.VkGroupDomainModel
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import javax.inject.Inject

class VkUserGroupsMapper @Inject constructor(){
    fun mapDataToDomain(data: UserGroupsResponse) : VkUserGroupsDomainModel {
        val userGroups: MutableList<VkGroupDomainModel> = mutableListOf()
        data.response?.let {
            it.groups?.forEach { group ->
                if (group.groupId != null) {
                    userGroups.add(
                        VkGroupDomainModel(
                        group.groupId!!,
                        group.groupName ?: "id: ${group.groupId}",
                        group.groupImgUrl ?: "https://vk.com/images/community_50.png"
                    )
                    )
                }
            }
        }

        return VkUserGroupsDomainModel(userGroups)
    }
}