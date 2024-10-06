package com.a1danz.feature_settings.domain.interactor.vk

import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.vk.id.AccessToken

interface VkUserInteractor {
    fun getUserVkConfig(): VkConfig?
    suspend fun saveVkToken(accessToken: AccessToken): VkConfig
    suspend fun getAllEditorGroups(): List<VkGroupInfo>
    suspend fun updateGroups(groups: List<VkGroupInfo>)
    suspend fun clearVkToken()
}