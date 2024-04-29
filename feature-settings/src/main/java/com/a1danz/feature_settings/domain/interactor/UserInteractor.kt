package com.a1danz.feature_settings.domain.interactor

import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.domain.interactor.vk.VkUserInteractor
import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_user_configurer.models.StateModel
import com.vk.id.AccessToken
import kotlinx.coroutines.flow.StateFlow

interface UserInteractor : VkUserInteractor {
}