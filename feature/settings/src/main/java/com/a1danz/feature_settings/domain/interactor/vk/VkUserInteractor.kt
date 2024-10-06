package com.a1danz.feature_settings.domain.interactor.vk

import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupsUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import com.vk.id.AccessToken

interface VkUserInteractor {
    fun getUserVkConfig(): VkConfig?
    fun getUserVkInfoUiModel(vkConfig: VkConfig): VkUserInfoUiModel
    suspend fun saveVkToken(accessToken: AccessToken): VkConfig
    suspend fun getUserGroups(): List<VkUserGroupUiModel>
    fun getVkUserInfo(): VkUserInfo
    suspend fun addGroup(vkGroupInfo: VkGroupInfo)
    suspend fun removeGroup(vkGroupInfo: VkGroupInfo)
    fun getUserSelectedVkGroups(): List<VkGroupInfo>
    suspend fun clearVkToken()
}