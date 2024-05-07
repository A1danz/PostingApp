package com.a1danz.feature_user_configurer.impl

import android.util.Log
import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.repo.UserFirestoreRepository
import com.a1danz.feature_user_configurer.repo.UserRepository
import com.a1danz.feature_user_configurer.utils.UnsubscriberImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class UserConfigurerImpl @Inject constructor(
    private val userModelDelegate: UserModelDelegate,
    private val userRepository: UserRepository,
    private val userFirestoreRepository: UserFirestoreRepository
) : UserConfigurer {
    private val user: User
        get() = userModelDelegate.user ?: throw IllegalStateException("User doesn't authorized")

    override suspend fun saveUser(user: User) {
        userRepository.saveUser(user)
    }

    override suspend fun initUser() {
        userModelDelegate.user = userRepository.getUser()
    }

    override suspend fun updateUserDelegate(userId: String) {
        var user: User? = userRepository.getUser()
        if (user == null) {
            user = userFirestoreRepository.getUser(userId)
            saveUser(user)
        }
        userModelDelegate.user = user
    }

    override suspend fun updateUserConfig(update: (Config) -> Config) {
        userRepository.updateConfig(update)
        user.config = update(user.config)
    }

    override suspend fun updateVkConfig(update: (VkConfig) -> VkConfig) {
        userRepository.updateVkConfig(update)
        user.config.vkConfig?.let(update)
    }

    override suspend fun clearVkConfig() {
        user.config.vkConfig = null
        userRepository.clearVkConfig()
    }

    override suspend fun updateTgConfig(update: (TgConfig) -> TgConfig) {
        Log.d("UC UPDATING", "BEFORE - ${user.config.tgConfig}")
        userRepository.updateTgConfig(update)
        if (user.config.tgConfig != null) {
            user.config.tgConfig = update(user.config.tgConfig!!)
        }
//        user.config.tgConfig?.let(update) - why this not working?
        Log.d("UPDATED CONFIG", "AFTER - ${user.config.tgConfig}")
    }

    override suspend fun listenTgUpdate(listenFlow: MutableStateFlow<Boolean?>): Unsubscriber {
        return userFirestoreRepository.listenTgUpdate(listenFlow, user.uId)
    }

    override suspend fun initTgToken() {
        val tgUserInfo = userFirestoreRepository.getUserTgInfo(user.uId) ?: return

        val config = TgConfig(tgUserInfo = tgUserInfo)
        userRepository.updateConfig {
            it.copy(tgConfig = config)
        }

        user.config.tgConfig = config
    }

    override suspend fun clearTgConfig() {
        user.config.tgConfig = null
        userRepository.clearTgConfig()
        userFirestoreRepository.clearTgInformation(user.uId)
    }
}
