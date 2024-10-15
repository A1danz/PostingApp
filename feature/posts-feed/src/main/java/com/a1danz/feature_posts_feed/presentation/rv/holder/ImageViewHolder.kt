package com.a1danz.feature_posts_feed.presentation.rv.holder

import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_posts_feed.databinding.ItemRvImageBinding
import com.a1danz.feature_posts_feed.presentation.model.ImageUiModel
import com.bumptech.glide.Glide

class ImageViewHolder(
    private val viewBinding: ItemRvImageBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(image: ImageUiModel) {
        Glide.with(itemView)
            .load(image.uri)
            .into(viewBinding.iv)
    }
}