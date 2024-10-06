package com.a1danz.feature_settings.presentation.screens.social_media.vk

import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.nav.GoBackRouter
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.vk.id.AccessToken
import javax.inject.Inject

class VkSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val vkSettingsRouter: GoBackRouter
) : BaseViewModel() {
    fun userHasToken(): Boolean {
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
//        return VkUserGroupsUiModel(emptyList())
        return userInteractor.getUserGroups()
    }

    fun getUserInfo(): VkUserInfo {
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
            userGroupUiModel.name,
            userGroupUiModel.imageUrl
        )

    }

    suspend fun unlinkVkUser() {
        userInteractor.clearVkToken()
        vkSettingsRouter.goBack()
    }
}