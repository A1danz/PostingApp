package com.a1danz.feature_settings.domain.interactor

import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_user_configurer.models.StateModel
import com.vk.id.AccessToken
import kotlinx.coroutines.flow.StateFlow

interface UserInteractor {
    suspend fun hasUserVkToken(): Boolean
    suspend fun saveVkToken(accessToken: AccessToken)
    suspend fun getUserGroups(): VkUserGroupsUiModel
    suspend fun saveVkGroupId()
}