package com.a1danz.feature_posts_feed.presentation.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_posts_feed.databinding.ItemRvPostInFeedBinding
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import com.a1danz.feature_posts_feed.presentation.rv.holder.PostViewHolder


class PostsAdapter(
    private val items: MutableList<PostUiModel>,
    private val onRemoveCallback: (PostsAdapter, PostUiModel, Int) -> Unit
) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemRvPostInFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            ::invokeRemoveCallback
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(itemPosition: Int) {
        items.removeAt(itemPosition)
        notifyItemRemoved(itemPosition)
    }

    private fun invokeRemoveCallback(itemPosition: Int) {
        onRemoveCallback.invoke(this, items[itemPosition], itemPosition)
    }
}