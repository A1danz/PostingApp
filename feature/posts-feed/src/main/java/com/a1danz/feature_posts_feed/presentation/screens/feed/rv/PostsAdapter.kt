package com.a1danz.feature_posts_feed.presentation.screens.feed.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_places_info.presentation.ui.SocialMediaTagView
import com.a1danz.feature_posts_feed.R
import com.a1danz.feature_posts_feed.databinding.ItemRvPostInFeedBinding
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel


class PostsAdapter(
    private val items: MutableList<PostUiModel>,
    private val onRemoveCallback: (PostsAdapter, PostUiModel, Int) -> Unit
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemRvPostInFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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

    inner class PostViewHolder(
        private val viewBinding: ItemRvPostInFeedBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.btnRemove.setOnClickListener {
                this@PostsAdapter.invokeRemoveCallback(adapterPosition)
            }
        }

        fun bindItem(postUiModel: PostUiModel) {
            with(viewBinding) {
                tvDate.text = postUiModel.date
                editText.setText(postUiModel.text)

                layoutPlaces.removeAllViews()
                postUiModel.postPlaces.forEach {
                    layoutPlaces.addView(
                        SocialMediaTagView(viewBinding.root.context, null).apply {
                            setStaticInfo(it)
                        }
                    )
                }
                if (postUiModel.imgs.isEmpty()) {
                    rvImages.visibility = View.GONE
                } else {
                    Log.d("IMAGE ADAPTER", "${rvImages.adapter}")

                    if (rvImages.adapter == null) {
                        val adapter = ImagesAdapter()
                        rvImages.adapter = adapter

                        val drawableDivider =
                            ContextCompat.getDrawable(itemView.context, R.drawable.images_divider)
                        if (drawableDivider != null) {
                            val dividerItemDecoration = DividerItemDecoration(
                                rvImages.context,
                                LinearLayoutManager.HORIZONTAL
                            )

                            dividerItemDecoration.setDrawable(drawableDivider)
                            rvImages.addItemDecoration(dividerItemDecoration)
                        }
                    }
                    (rvImages.adapter as? ImagesAdapter)?.submitList(postUiModel.imgs)
                }



            }
        }
    }
}