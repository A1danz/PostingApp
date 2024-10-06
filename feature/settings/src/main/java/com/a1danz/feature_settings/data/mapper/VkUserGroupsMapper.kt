package com.a1danz.feature_settings.data.mapper

import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.feature_settings.data.remote.vk.pojo.UserGroupsResponse
import javax.inject.Inject

class VkUserGroupsMapper @Inject constructor(){
    fun mapDataToDomain(data: UserGroupsResponse) : List<VkGroupInfo> {
        return data.response.groups.map {
            VkGroupInfo(
                groupId = it.groupId,
                groupName = it.groupName,
                img = it.groupImgUrl
            )
        }
    }
}