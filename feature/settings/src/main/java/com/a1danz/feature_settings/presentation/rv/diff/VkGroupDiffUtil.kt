package com.a1danz.feature_settings.presentation.rv.diff

import androidx.recyclerview.widget.DiffUtil
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import javax.inject.Inject

class VkGroupDiffUtil : DiffUtil.ItemCallback<VkUserGroupUiModel>() {
    override fun areItemsTheSame(oldItem: VkUserGroupUiModel, newItem: VkUserGroupUiModel): Boolean {
        return oldItem.groupId == newItem.groupId
    }

    override fun areContentsTheSame(oldItem: VkUserGroupUiModel, newItem: VkUserGroupUiModel): Boolean {
        return oldItem == newItem
    }
}