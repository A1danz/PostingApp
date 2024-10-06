package com.a1danz.feature_settings.presentation.rv.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_settings.databinding.ItemRvVkGroupBinding
import com.a1danz.feature_settings.databinding.ItemRvVkGroupPreviewBinding
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.rv.Config
import com.a1danz.feature_settings.presentation.rv.diff.VkGroupDiffUtil
import com.a1danz.feature_settings.presentation.rv.holder.VkGroupLoadingViewHolder
import com.a1danz.feature_settings.presentation.rv.holder.VkGroupViewHolder
import com.bumptech.glide.Glide

class VkGroupAdapter(
    private val chosenCallback: (VkUserGroupUiModel, Boolean) -> Unit
) : ListAdapter<VkUserGroupUiModel, ViewHolder>(VkGroupDiffUtil()) {
    private var isLoading = true
        set(value) {
            if (value != isLoading) {
                field = value
                notifyItemRangeChanged(0, Config.LOADING_ITEM_COUNT)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_LOADED) {
            VkGroupViewHolder(
                viewBinding = ItemRvVkGroupBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                chosenCallback = chosenCallback
            )
        } else {
            VkGroupLoadingViewHolder(
                ItemRvVkGroupPreviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_LOADING -> {}
            VIEW_TYPE_LOADED -> {
                (holder as? VkGroupViewHolder)?.bindItem(getItem(position))
                    ?: Log.e("ERR", "Failed cast to VkGroupViewHolder - $holder")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_LOADING else VIEW_TYPE_LOADED
    }

    override fun getItemCount(): Int {
        return if (isLoading) Config.LOADING_ITEM_COUNT else super.getItemCount()
    }

    fun submitListWithDisablingLoading(items: List<VkUserGroupUiModel>) {
        isLoading = false
        submitList(items)
    }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_LOADED = 1
    }
}
