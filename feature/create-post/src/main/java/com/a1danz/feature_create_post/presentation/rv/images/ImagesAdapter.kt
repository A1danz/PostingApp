package com.a1danz.feature_create_post.presentation.rv.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_create_post.databinding.ItemRvAttachedImageBinding
import com.a1danz.feature_create_post.presentation.model.ImageUiModel
import com.a1danz.feature_create_post.presentation.rv.images.diff.ImageDiffUtil
import com.bumptech.glide.Glide

class ImagesAdapter : ListAdapter<ImageUiModel, ImagesAdapter.ImageViewHolder>(ImageDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemRvAttachedImageBinding.inflate(
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
        private val viewBinding: ItemRvAttachedImageBinding
    ) : ViewHolder(viewBinding.root) {

        fun bindItem(image: ImageUiModel) {
            with(viewBinding) {
                Glide.with(this.root)
                    .load(image.imgUri)
                    .into(iv)
            }

        }
    }
}