package com.a1danz.feature_user_configurer.impl

import android.util.Log
import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.domain.repository.UserRemoteRepository
import com.a1danz.feature_user_configurer.domain.repository.UserLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class UserConfigurerImpl @Inject constructor(
    private val userModelDelegate: UserModelDelegate,
    private val userLocalRepository: UserLocalRepository,
    private val userRemoteRepository: UserRemoteRepository
) : UserConfigurer {

    private val user: User
        get() = userModelDelegate.user ?: throw IllegalStateException("User doesn't initialized")

    override suspend fun updateUserDelegate(userId: String) {
        var user: User? = userLocalRepository.getUser()
        if (user == null) {
            user = userRemoteRepository.getUser(userId)
            if (user != null) {
                userLocalRepository.saveUser(user)
            }

        }
        userModelDelegate.user = user
    }

    override suspend fun updateUserConfig(update: (Config) -> Config) {
        userLocalRepository.updateConfig(update)
        user.config = update(user.config)
    }

    override suspend fun updateVkConfig(update: (VkConfig) -> VkConfig) {
        userLocalRepository.updateVkConfig(update)
        user.config.vkConfig = user.config.vkConfig?.let(update)
    }

    override suspend fun clearVkConfig() {
        user.config.vkConfig = null
        userLocalRepository.clearVkConfig()
    }

    override suspend fun updateTgConfig(update: (TgConfig) -> TgConfig) {
        userLocalRepository.updateTgConfig(update)
        user.config.tgConfig = user.config.tgConfig?.let(update)
    }

    override suspend fun listenTgUpdate(listenFlow: MutableStateFlow<Boolean?>): Unsubscriber {
        return userRemoteRepository.listenTgUpdate(listenFlow, user.uId)
    }

    override suspend fun initTgConfig() {
        val tgUserInfo = userRemoteRepository.getUserTgInfo(user.uId) ?: return

        val config = TgConfig(tgUserInfo = tgUserInfo)

        userLocalRepository.updateConfig {
            it.copy(tgConfig = config)
        }

        user.config.tgConfig = config
    }

    override suspend fun clearTgConfig() {
        user.config.tgConfig = null
        userLocalRepository.clearTgConfig()
        userRemoteRepository.clearTgInformation(user.uId)
    }
}
