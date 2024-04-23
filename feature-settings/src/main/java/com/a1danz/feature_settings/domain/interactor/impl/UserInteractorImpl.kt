package com.a1danz.feature_settings.domain.interactor.impl

import android.util.Log
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.mapper.VkUserGroupsDomainMapper
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.domain.repository.VkRepository
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.models.StateModel
import com.vk.id.AccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val userConfigurer: UserConfigurer,
    private val vkRepository: VkRepository,
    private val user: User,
    private val userGroupsDomainMapper: VkUserGroupsDomainMapper
) : UserInteractor {

    override suspend fun hasUserVkToken(): Boolean {
        return userConfigurer.hasUserVkToken()
    }

    override suspend fun saveVkToken(accessToken: AccessToken) {
        userConfigurer.saveVkToken(accessToken)
        Log.d("SAVED", "SAVED ${user.vkToken == null}")
    }

    override suspend fun getUserGroups(): VkUserGroupsUiModel {
        return userGroupsDomainMapper.mapToUiModel(vkRepository.getUserEditGroups())
    }

    override suspend fun saveVkGroupId() {
        TODO("Not yet implemented")
    }
}