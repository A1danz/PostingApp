package com.a1danz.feature_posts_feed.presentation.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_posts_feed.databinding.ViewLoadStateBinding

class PostsLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<PostsLoadStateAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return ItemViewHolder(
            ViewLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    inner class ItemViewHolder(private val viewBinding: ViewLoadStateBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(state: LoadState) {
            with(viewBinding) {
                progressBar.isVisible = state == LoadState.Loading
                retryBtn.isVisible = state is LoadState.Error
                tvError.isVisible = state is LoadState.Error
            }
        }
    }
}