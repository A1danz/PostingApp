package com.a1danz.feature_settings.domain.mapper

import com.a1danz.feature_settings.domain.model.VkUserGroupsDomainModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import javax.inject.Inject

class VkUserGroupsDomainMapper @Inject constructor() {
    fun mapToUiModel(domainModel: VkUserGroupsDomainModel): VkUserGroupsUiModel {
        return VkUserGroupsUiModel(domainModel.userGroups.map { group ->
            VkUserGroupUiModel(
                group.name,
                group.imgUrl,
                group.id
            )
        })
    }
}