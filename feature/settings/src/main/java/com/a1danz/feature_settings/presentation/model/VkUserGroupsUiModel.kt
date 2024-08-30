package com.a1danz.feature_settings.presentation.model

class VkUserGroupsUiModel (
    val groups: List<VkUserGroupUiModel>
)

class VkUserGroupUiModel(
    val name: String,
    val imageUrl: String,
    val groupId: Long,
    var isChosen: Boolean = false
)