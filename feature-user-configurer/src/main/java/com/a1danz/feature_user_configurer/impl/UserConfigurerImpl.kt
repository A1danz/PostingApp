package com.a1danz.feature_user_configurer.impl

import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
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

    override suspend fun saveVkToken(accessToken: AccessToken) {
        val vkAccessToken = VkAccessToken(accessToken.token, accessToken.userID)
        user.vkToken = vkAccessToken

        userRepository.saveVkToken(vkAccessToken)
    }

    override suspend fun initVkToken() {
        val accessToken: VkAccessToken? = userRepository.getVkToken()
        user.vkToken = accessToken
    }

    override suspend fun initUser() {
       initVkToken()
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
        return user.vkToken != null
    }

    override suspend fun saveVkGroupdId() {
        return
    }
}