package com.a1danz.feature_user_configurer.repo

import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
    suspend fun updateConfig(update: (Config) -> Config)
    suspend fun updateVkConfig(update: (VkConfig) -> VkConfig)
    suspend fun clearVkConfig()

    suspend fun updateTgConfig(update: (TgConfig) -> TgConfig)
    suspend fun clearTgConfig()
}