package com.a1danz.feature_settings.presentation.screens.social_media.vk.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_settings.databinding.GroupItemLayoutBinding
import com.a1danz.feature_settings.databinding.ItemRvVkGroupBinding
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.bumptech.glide.Glide

class VkGroupAdapter(
    private val items: List<VkUserGroupUiModel>,
    private val chosenCallback: (Boolean, VkUserGroupUiModel) -> Unit
) : RecyclerView.Adapter<VkGroupAdapter.VkGroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkGroupViewHolder {
        return VkGroupViewHolder(
            ItemRvVkGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VkGroupViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
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
                    chosenCallback.invoke(isChecked, item)
                }
            }
        }
    }
}
