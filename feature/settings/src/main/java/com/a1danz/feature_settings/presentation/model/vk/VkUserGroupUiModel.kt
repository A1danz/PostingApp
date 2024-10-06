package com.a1danz.feature_settings.presentation.model.vk

data class VkUserGroupUiModel(
    val name: String,
    val imageUrl: String,
    val groupId: Long,
    var isChosen: Boolean = false
)