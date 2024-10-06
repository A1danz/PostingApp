package com.a1danz.feature_settings.presentation.rv.holder

import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_settings.databinding.ItemRvVkGroupBinding
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.bumptech.glide.Glide

class VkGroupViewHolder(
    private val viewBinding: ItemRvVkGroupBinding,
    private val chosenCallback: (VkUserGroupUiModel, Boolean) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    var item: VkUserGroupUiModel? = null

    init {
        viewBinding.switcher.setOnCheckedChangeListener { btnView, isChecked ->
            item?.let {
                chosenCallback.invoke(it, isChecked)
            }
        }
    }

    fun bindItem(item: VkUserGroupUiModel) {
        this.item = item

        with(viewBinding) {
            Glide.with(root)
                .load(item.imageUrl)
                .into(ivGroupImg)

            tvGroupName.text = item.name

            switcher.isChecked = item.isChosen
        }
    }
}