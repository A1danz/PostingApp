package com.a1danz.feature_user_configurer.impl

import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.repo.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.vk.id.AccessToken
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserConfigurerImpl @Inject constructor(
    private val userModelDelegate: UserModelDelegate,
    private val userRepository: UserRepository,
    private val firestore: FirebaseFirestore
) : UserConfigurer {
    private val user: User get() = userModelDelegate.user ?: throw IllegalStateException("User doesn't authorized")

    override suspend fun saveUser(user: User) {
        userRepository.saveUser(user)
    }

    override suspend fun initUser() {
       userModelDelegate.user = userRepository.getUser()
    }

    override suspend fun updateUserDelegate(userId: String) {
        var user: User? = userRepository.getUser()
        if (user == null) {
            val userDoc = firestore.collection("users").document(userId).get().await()
            val name = userDoc.getString("name") ?: "User"
            user = User(userId, name = name)
            saveUser(user)
        }
        userModelDelegate.user = user
    }

    override suspend fun hasUserVkToken(): Boolean {
        return user.config.vkConfig != null
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

    override suspend fun getSelectedGroups(): List<VkGroupInfo> {
        return user.config.vkConfig?.userGroups ?: emptyList()
    }
}