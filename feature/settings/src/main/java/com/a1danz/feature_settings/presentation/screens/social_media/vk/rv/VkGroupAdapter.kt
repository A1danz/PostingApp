package com.a1danz.feature_settings.presentation.screens.social_media.vk.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_settings.databinding.ItemRvVkGroupBinding
import com.a1danz.feature_settings.databinding.ItemRvVkGroupPreviewBinding
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.bumptech.glide.Glide

class VkGroupAdapter(
    private val chosenCallback: (VkUserGroupUiModel, Boolean) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    private var items: List<VkUserGroupUiModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_LOADED) {
            VkGroupViewHolder(
                ItemRvVkGroupBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            LoadingViewHolder(
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
                items?.get(position)?.let {
                    (holder as? VkGroupViewHolder)?.bindItem(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 10
    }

    override fun getItemViewType(position: Int): Int {
        return if (items == null) VIEW_TYPE_LOADING
        else VIEW_TYPE_LOADED
    }

    fun setItems(items: List<VkUserGroupUiModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class VkGroupViewHolder(
        private val viewBinding: ItemRvVkGroupBinding
    ) : ViewHolder(viewBinding.root) {
        fun bindItem(item: VkUserGroupUiModel) {
            with(viewBinding) {
                tvGroupName.text = item.name
                Glide.with(root)
                    .load(item.imageUrl)
                    .into(ivGroupImg)

                switcher.isChecked = item.isChosen
                switcher.setOnCheckedChangeListener { btnView, isChecked ->
                    chosenCallback.invoke(item, isChecked)
                }
            }
        }
    }

    inner class LoadingViewHolder(
        private val viewBinding: ItemRvVkGroupPreviewBinding
    ) : ViewHolder(viewBinding.root)

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_LOADED = 1
    }
}
