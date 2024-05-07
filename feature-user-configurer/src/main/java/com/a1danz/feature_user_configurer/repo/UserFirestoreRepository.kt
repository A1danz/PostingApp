package com.a1danz.feature_user_configurer.repo

import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow

interface UserFirestoreRepository {
    suspend fun getUser(userId: String): User
    suspend fun listenTgUpdate(listenFlow: MutableStateFlow<Boolean?>, userId: String): Unsubscriber
    suspend fun getUserTgInfo(userId: String): TgUserInfo?
    suspend fun clearTgInformation(userId: String)
}