package com.a1danz.feature_settings.domain.interactor.impl

import android.util.Log
import androidx.room.util.copy
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.mapper.VkUserGroupsDomainMapper
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.domain.repository.VkRepository
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.models.StateModel
import com.vk.id.AccessToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val userConfigurer: UserConfigurer,
    private val vkRepository: VkRepository,
    private val user: User,
    private val userGroupsDomainMapper: VkUserGroupsDomainMapper,
    private val dispatcher: CoroutineDispatcher
) : UserInteractor {

    override suspend fun hasUserVkToken(): Boolean {
        return withContext(dispatcher) {
            userConfigurer.hasUserVkToken()
        }
    }

    override suspend fun saveVkToken(accessToken: AccessToken) {
        withContext(dispatcher) {
            userConfigurer.updateUserConfig {
                it.copy(vkConfig = VkConfig(userId = accessToken.userID, accessToken = accessToken.token))
            }
        }
    }

    override suspend fun getUserGroups(): VkUserGroupsUiModel {
        return withContext(dispatcher) {
            userGroupsDomainMapper.mapToUiModel(vkRepository.getUserEditGroups())
        }
    }

    override suspend fun saveVkGroupId(groupId: Long) {
        withContext(dispatcher) {
            userConfigurer.updateUserConfig {
                it.copy(vkConfig = it.vkConfig?.let { vkConfig ->
                    vkConfig.userGroups.clear()
                    vkConfig.userGroups.add(groupId)
                    vkConfig.copy()
                })
            }
        }
    }
}