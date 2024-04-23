package com.a1danz.feature_settings.domain.repository

import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel

interface VkRepository {
    suspend fun getUserEditGroups(): VkUserGroupsDomainModel
}