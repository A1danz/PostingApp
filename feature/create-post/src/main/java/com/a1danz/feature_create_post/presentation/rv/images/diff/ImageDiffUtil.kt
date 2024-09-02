package com.a1danz.feature_create_post.presentation.rv.images.diff

import androidx.recyclerview.widget.DiffUtil
import com.a1danz.feature_create_post.presentation.model.ImageUiModel

class ImageDiffUtil : DiffUtil.ItemCallback<ImageUiModel>() {
    override fun areItemsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean {
        return oldItem.imgUri == newItem.imgUri
    }
}