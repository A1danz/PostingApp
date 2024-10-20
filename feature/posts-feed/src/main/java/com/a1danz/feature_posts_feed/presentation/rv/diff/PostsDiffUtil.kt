package com.a1danz.feature_posts_feed.presentation.rv.diff

import androidx.recyclerview.widget.DiffUtil
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel

class PostsDiffUtil : DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem == newItem
    }
}