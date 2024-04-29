package com.a1danz.feature_settings.domain.interactor.impl

import android.util.Log
import androidx.room.util.copy
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.mapper.VkUserGroupsDomainMapper
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.domain.repository.VkRepository
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
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
                Log.d("PHOTOS", "${accessToken.userData.photo50}, ${accessToken.userData.photo100}, ${accessToken.userData.photo200}")
                var photo = accessToken.userData.photo200
                if (photo == null) photo = accessToken.userData.photo100
                if (photo == null) photo = accessToken.userData.photo50
                it.copy(vkConfig = VkConfig(
                    userId = accessToken.userID,
                    accessToken = accessToken.token,
                    userInfo = VkUserInfo(
                        userImg = photo,
                        fullName = accessToken.userData.firstName + " " + accessToken.userData.lastName
                    ),
                ))
            }
        }
    }

    override suspend fun getUserGroups(): VkUserGroupsUiModel {
        return withContext(dispatcher) {
            val groupsUiModel = userGroupsDomainMapper.mapToUiModel(vkRepository.getUserEditGroups())
            val groups = HashSet<VkUserGroupUiModel>(groupsUiModel.groups)
//            val selectedGroups = HashSet<VkGroupInfo>(userConfigurer.getSelectedGroups())
//            selectedGroups.forEach {selectedGroup ->
//                groups.find {
//                    selectedGroup.groupId == it.groupId
//                }?.isChosen = true
//            }

            VkUserGroupsUiModel(groups.toList().sortedBy { it.isChosen })

        }
    }

    override suspend fun getVkUserInfo(): VkUserInfo {
        return user.config.vkConfig?.userInfo ?: throw IllegalStateException("User unauthorized")
    }

    override suspend fun addGroup(vkGroupInfo: VkGroupInfo) {
        withContext(dispatcher) {
            userConfigurer.updateVkConfig { vkConfig ->
                vkConfig.userGroups.add(vkGroupInfo)
                vkConfig.copy()
            }
        }
    }

    override suspend fun removeGroup(vkGroupInfo: VkGroupInfo) {
        withContext(dispatcher) {
            userConfigurer.updateVkConfig { vkConfig ->
                vkConfig.userGroups.remove(vkGroupInfo)
                vkConfig.copy()
            }
        }
    }

    override suspend fun getUserSelectedVkGroups(): List<VkGroupInfo> {
        return withContext(dispatcher) {
            user.config.vkConfig?.userGroups ?: listOf()
        }
    }

    override suspend fun clearVkToken() {
        withContext(dispatcher) {
            userConfigurer.clearVkConfig()
        }
    }
}