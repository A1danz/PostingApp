package com.a1danz.feature_user_configurer.repo

import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun saveVkToken(vkAccessToken: VkAccessToken)
    suspend fun getVkToken(): VkAccessToken?
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}