package com.a1danz.feature_settings.data.repository.vk

import com.a1danz.feature_settings.data.mapper.VkUserGroupsMapper
import com.a1danz.feature_settings.data.remote.vk.VkApi
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.domain.repository.VkRepository
import javax.inject.Inject

class VkRepositoryImpl @Inject constructor(
    private val vkApi: VkApi,
    private val domainMapper: VkUserGroupsMapper
) : VkRepository {
    override suspend fun getUserEditGroups(): VkUserGroupsDomainModel {
        return domainMapper.mapDataToDomain(
            vkApi.getUserEditGroups()
        )
    }
}