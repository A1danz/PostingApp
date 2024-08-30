package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewPostPublishingItemBinding
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingStatusUiModel
import com.bumptech.glide.Glide

class PostPublishingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_post_publishing_item, this)
    }

    private val viewBinding: ViewPostPublishingItemBinding by viewBinding(ViewPostPublishingItemBinding::bind)

    fun setGlobalStatus(status: String) {
        viewBinding.tvGlobalStatus.text = status
    }

    fun setPublishingStatus(status: PostPublishingStatusUiModel) {
        with(viewBinding) {
            tvPublishingStatus.text = status.uiName
            tvPublishingStatus.setTextColor(ContextCompat.getColor(context, status.color))
            tvGlobalStatus.text = status.globalStatus
        }
    }

    fun setItemName(name: String) {
        viewBinding.tvName.text = name
    }

    fun setImage(uri: String) {
        Glide.with(this)
            .load(uri)
            .into(viewBinding.ivImg)
    }

    fun setItemModel(itemModel: PostPublishingItemDomainModel) {
        setItemName(itemModel.itemInfo.name)
        setImage(itemModel.itemInfo.img)
    }
}