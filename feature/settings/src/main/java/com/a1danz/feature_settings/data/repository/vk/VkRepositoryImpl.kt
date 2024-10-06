package com.a1danz.feature_settings.data.repository.vk

import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.feature_settings.data.mapper.VkUserGroupsMapper
import com.a1danz.feature_settings.data.remote.vk.api.VkApi
import com.a1danz.feature_settings.domain.repository.VkRepository
import javax.inject.Inject

class VkRepositoryImpl @Inject constructor(
    private val vkApi: VkApi,
    private val domainMapper: VkUserGroupsMapper
) : VkRepository {
    override suspend fun getUserEditGroups(): List<VkGroupInfo> {
        return domainMapper.mapDataToDomain(
            vkApi.getUserEditGroups()
        )
    }
}