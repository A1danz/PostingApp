package com.a1danz.feature_settings.domain.repository

import com.a1danz.common.domain.model.VkGroupInfo

interface VkRepository {
    suspend fun getUserEditGroups(): List<VkGroupInfo>
}