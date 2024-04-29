package com.a1danz.feature_user_configurer

import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.vk.id.AccessToken

interface UserConfigurer {
    suspend fun initUser()
    suspend fun saveUser(user: User)
    suspend fun updateUserDelegate(userId: String)
    suspend fun hasUserVkToken(): Boolean
    suspend fun updateUserConfig(update: (Config) -> Config)
    suspend fun updateVkConfig(update: (VkConfig) -> VkConfig)
    suspend fun getSelectedGroups(): List<VkGroupInfo>
    suspend fun clearVkConfig()
}