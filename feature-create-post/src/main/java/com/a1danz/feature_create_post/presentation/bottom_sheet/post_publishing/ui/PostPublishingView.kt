package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewPostPublishingBinding
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel

class PostPublishingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_post_publishing, this)
    }

    private val viewBinding: ViewPostPublishingBinding by viewBinding(ViewPostPublishingBinding::bind)

    fun setPlaceInfo(placeStaticInfo: PostPlaceStaticInfo) {
        with(viewBinding) {
            tvPlaceName.text = placeStaticInfo.title
        }
    }

    fun addItem(item: PostPublishingItemDomainModel): PostPublishingItemView {
        val postPublishingItemView = PostPublishingItemView(context, null).apply {
            setItemModel(item)
        }
        viewBinding.layoutItems.addView(postPublishingItemView)
        return postPublishingItemView
    }
}