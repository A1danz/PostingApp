package com.a1danz.feature_posts_feed.presentation.rv.diff

import androidx.recyclerview.widget.DiffUtil
import com.a1danz.feature_posts_feed.presentation.model.ImageUiModel

class ImageModelDiffUtil : DiffUtil.ItemCallback<ImageUiModel>() {
    override fun areItemsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean {
        return oldItem == newItem
    }
}