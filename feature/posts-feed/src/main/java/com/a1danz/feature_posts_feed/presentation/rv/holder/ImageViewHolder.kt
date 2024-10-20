package com.a1danz.feature_posts_feed.presentation.rv.holder

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.size.Scale
import com.a1danz.feature_posts_feed.databinding.ItemRvImageBinding
import com.a1danz.feature_posts_feed.presentation.model.ImageUiModel

class ImageViewHolder(
    private val viewBinding: ItemRvImageBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(image: ImageUiModel) {
        viewBinding.iv.load(image.uri)
    }
}