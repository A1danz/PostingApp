package com.a1danz.feature_settings.presentation.screens.social_media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_settings.presentation.navigation.SocialMediaRouter
import com.a1danz.feature_user_configurer.models.StateModel
import com.vk.id.AccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SocialMediaSettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val socialMediaRouter: SocialMediaRouter
) : ViewModel() {

    suspend fun userHasVkToken(): Boolean {
        return userInteractor.hasUserVkToken()

    }

    fun openVkSettingsScreen() {
        socialMediaRouter.openVkSettings()
    }

    fun openTgSettingsScreen() {
        return
    }

    suspend fun getUserInfo(): VkUserInfo {
        return userInteractor.getVkUserInfo()
    }

    suspend fun getUserSelectedVkGroups(): List<VkGroupInfo> {
        return userInteractor.getUserSelectedVkGroups()
    }

}