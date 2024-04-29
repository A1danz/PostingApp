package com.a1danz.feature_settings.presentation.screens.social_media.vk

import androidx.lifecycle.ViewModel
import com.a1danz.common.domain.model.VkAccessToken
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_settings.presentation.screens.social_media.vk.nav.VkSettingsRouter
import com.vk.id.AccessToken
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VkSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val vkSettingsRouter: VkSettingsRouter
) : ViewModel() {
    suspend fun userHasToken(): Boolean {
        return userInteractor.hasUserVkToken()
    }

    suspend fun saveVkToken(accessToken: AccessToken): Boolean {
        runCatching {
            userInteractor.saveVkToken(accessToken)
        }.onSuccess {
            return true
        }
        return false
    }

    suspend fun getVkUserGroups(): VkUserGroupsUiModel {
        return userInteractor.getUserGroups()
    }

    suspend fun getUserInfo(): VkUserInfo {
        return userInteractor.getVkUserInfo()
    }

    suspend fun addVkGroup(groupUiModel: VkUserGroupUiModel) {
        userInteractor.addGroup(narrowUserGroupDown(groupUiModel))
    }

    suspend fun removeGroup(groupUiModel: VkUserGroupUiModel) {
        userInteractor.removeGroup(narrowUserGroupDown(groupUiModel))
    }

    private fun narrowUserGroupDown(userGroupUiModel: VkUserGroupUiModel): VkGroupInfo {
        return VkGroupInfo(
            userGroupUiModel.groupId,
            userGroupUiModel.name
        )

    }

    suspend fun unlinkVkUser() {
        userInteractor.clearVkToken()
        vkSettingsRouter.goBack()
    }
}