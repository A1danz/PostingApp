package com.a1danz.feature_create_post.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_create_post.databinding.ItemRvPostPlaceBinding
import com.a1danz.feature_create_post.presentation.model.PostPlaceDetailInfoUiModel
import com.a1danz.feature_create_post.presentation.ui.SocialMediaTagView
import com.bumptech.glide.Glide

class PostPlacesAdapter(
    private val items: List<PostPlaceDetailInfoUiModel>
) : RecyclerView.Adapter<PostPlacesAdapter.PostPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostPlaceViewHolder {
        return PostPlaceViewHolder(
            ItemRvPostPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostPlaceViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostPlaceViewHolder(private val viewBinding: ItemRvPostPlaceBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindItem(item: PostPlaceDetailInfoUiModel) {
            with(viewBinding) {
                tvName.text = item.name
                Glide.with(iv)
                    .load(item.img)
                    .into(iv)

                layout.addView(
                    SocialMediaTagView(context = viewBinding.root.context, null).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        setStaticInfo(item.staticInfo)
                    }
                )
            }
        }
    }
}