package com.a1danz.feature_posts_feed.presentation.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.a1danz.feature_posts_feed.databinding.ItemRvPostInFeedBinding
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import com.a1danz.feature_posts_feed.presentation.rv.diff.PostsDiffUtil
import com.a1danz.feature_posts_feed.presentation.rv.holder.PostViewHolder

class PostsAdapter(
    private val onRemoveCallback: (PostUiModel) -> Unit
) : PagingDataAdapter<PostUiModel, PostViewHolder>(PostsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            viewBinding = ItemRvPostInFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            removeBtnClickedCallback = onRemoveCallback
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let(holder::bindItem)
    }
}