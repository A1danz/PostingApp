package com.a1danz.feature_user_configurer

import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import kotlinx.coroutines.flow.MutableStateFlow

interface UserConfigurer {
    suspend fun updateUserDelegate(userId: String)

    suspend fun updateUserConfig(update: (Config) -> Config)
    suspend fun updateVkConfig(update: (VkConfig) -> VkConfig)
    suspend fun clearVkConfig()

    suspend fun updateTgConfig(update: (TgConfig) -> TgConfig)
    suspend fun listenTgUpdate(listenFlow: MutableStateFlow<Boolean?>): Unsubscriber
    suspend fun initTgConfig()
    suspend fun clearTgConfig()
}