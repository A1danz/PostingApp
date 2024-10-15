package com.a1danz.feature_posts_feed.presentation.rv.holder

import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_places_info.presentation.ui.SocialMediaTagView
import com.a1danz.feature_posts_feed.R
import com.a1danz.feature_posts_feed.databinding.ItemRvPostInFeedBinding
import com.a1danz.feature_posts_feed.presentation.model.ImageUiModel
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import com.a1danz.feature_posts_feed.presentation.rv.adapter.ImagesAdapter
import com.a1danz.feature_posts_feed.presentation.rv.decoration.ImagesItemDecoration

class PostViewHolder(
    private val viewBinding: ItemRvPostInFeedBinding,
    private val removeBtnClickedCallback: (Int) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    private val imagesAdapter by lazy {
        ImagesAdapter()
    }

    init {
        with(viewBinding) {
            btnRemove.setOnClickListener {
                removeBtnClickedCallback.invoke(adapterPosition)
            }

            rvImages.adapter = imagesAdapter
            rvImages.addItemDecoration(ImagesItemDecoration(root.resources.getDimensionPixelSize(R.dimen.images_horizontal_space)))
        }
    }

    fun bindItem(postUiModel: PostUiModel) {
        with(viewBinding) {
            tvDate.text = postUiModel.date
            editText.setText(postUiModel.text)

            layoutPlaces.apply {
                removeAllViews()

                postUiModel.postPlaces.forEach {
                    addView(
                        SocialMediaTagView(viewBinding.root.context, null).apply {
                            setPostPlaceUiInfo(it)
                        }
                    )
                }
            }


            rvImages.isGone = postUiModel.imgs.isEmpty()
            imagesAdapter.submitList(postUiModel.imgs.map { ImageUiModel(it) })
        }
    }
}