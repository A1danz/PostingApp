package com.a1danz.feature_posts_feed.presentation.screens.feed.rv

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_posts_feed.R
import com.a1danz.feature_posts_feed.databinding.ItemRvImageBinding
import com.bumptech.glide.Glide

class ImagesAdapter : ListAdapter<Uri, ImagesAdapter.ImageViewHolder>(object : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }
}) {


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

    inner class ImageViewHolder(
        private val viewBinding: ItemRvImageBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindItem(image: Uri) {
            Glide.with(itemView)
                .load(image)
                .into(viewBinding.iv)
        }
    }
}