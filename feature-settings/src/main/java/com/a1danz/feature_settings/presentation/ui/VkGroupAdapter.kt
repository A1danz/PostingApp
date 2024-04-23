package com.a1danz.feature_settings.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.GroupItemLayoutBinding
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.bumptech.glide.Glide

class VkGroupAdapter(
    private val context: Context,
    private val items: List<VkUserGroupUiModel>
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.group_item_layout, parent, false)
        val viewBinding = GroupItemLayoutBinding.bind(view)

        val item = getItem(position)
        item.let { group ->
            with(viewBinding) {
                tvName.text = group.name
                Glide.with(context)
                    .load(group.imageUrl)
                    .into(ivImage)
            }
        }
        return viewBinding.root
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): VkUserGroupUiModel {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}