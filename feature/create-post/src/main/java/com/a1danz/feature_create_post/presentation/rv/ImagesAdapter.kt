package com.a1danz.feature_create_post.presentation.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_create_post.databinding.ItemRvAttachedImageBinding
import com.a1danz.feature_create_post.presentation.model.ImageModel
import com.bumptech.glide.Glide

class ImagesAdapter : ListAdapter<ImageModel, ImagesAdapter.ImageViewHolder>(object: DiffUtil.ItemCallback<ImageModel>() {
    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        Log.e("SAME", "ITEMS ARE SAMe")
        return oldItem.imgUri == newItem.imgUri
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem.imgUri == newItem.imgUri
    }
}) {

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

    inner class ImageViewHolder(private val viewBinding: ItemRvAttachedImageBinding)
        : ViewHolder(viewBinding.root) {

            fun bindItem(image: ImageModel) {
                with(viewBinding) {
                    Glide.with(this.root)
                        .load(image.imgUri)
                        .into(iv)
                }

            }
        }
}