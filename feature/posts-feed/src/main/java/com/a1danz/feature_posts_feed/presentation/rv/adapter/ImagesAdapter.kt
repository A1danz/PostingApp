package com.a1danz.feature_posts_feed.presentation.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.a1danz.feature_posts_feed.databinding.ItemRvImageBinding
import com.a1danz.feature_posts_feed.presentation.model.ImageUiModel
import com.a1danz.feature_posts_feed.presentation.rv.diff.ImageModelDiffUtil
import com.a1danz.feature_posts_feed.presentation.rv.holder.ImageViewHolder

class ImagesAdapter : ListAdapter<ImageUiModel, ImageViewHolder>(ImageModelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemRvImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}