package com.a1danz.feature_user_configurer

import com.a1danz.common.domain.model.User
import com.vk.id.AccessToken

interface UserConfigurer {
    suspend fun saveVkToken(accessToken: AccessToken)
    suspend fun initVkToken()
    suspend fun initUser()
    suspend fun saveUser(user: User)
    suspend fun updateUserDelegate(userId: String)
    suspend fun hasUserVkToken(): Boolean
    suspend fun saveVkGroupdId()
}