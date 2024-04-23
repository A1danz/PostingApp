package com.a1danz.feature_user_configurer.repo.impl

import com.a1danz.common.core.datastore.DataStoreManager
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import com.a1danz.feature_user_configurer.repo.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : UserRepository {

    override suspend fun saveVkToken(vkAccessToken: VkAccessToken) {
        dataStoreManager.saveString(VK_TOKEN, vkAccessToken.accessToken)
        dataStoreManager.saveLong(VK_USER_ID, vkAccessToken.userId)
    }

    override suspend fun getVkToken(): VkAccessToken? {
        val stringToken = dataStoreManager.getString(VK_TOKEN, "").first()
        val userId = dataStoreManager.getLong(VK_USER_ID, -1).first()
        if (stringToken.isBlank() || userId < 0) return null
        return VkAccessToken(stringToken, userId)
    }

    override suspend fun getUser(): User? {
        val userUid = dataStoreManager.getString(USER_UID, "").first()
        if (userUid.isBlank()) return null
        val username = dataStoreManager.getString(USER_NAME, "User").first()
        return User(userUid, name=username)
    }

    override suspend fun saveUser(user: User) {
        dataStoreManager.saveString(USER_UID, user.uId)
        dataStoreManager.saveString(USER_NAME, user.name)
    }

    companion object {
        private val VK_TOKEN = "VK_TOKEN"
        private val VK_USER_ID = "VK_USER_ID"
        private val USER_NAME = "USER_NAME"
        private val USER_UID = "USER_UID"
    }
}