package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.databinding.ViewPostPublishingBinding
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo

class PostPublishingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val viewBinding: ViewPostPublishingBinding by viewBinding(ViewPostPublishingBinding::bind)

    fun setPlaceInfo(placeStaticInfo: PostPlaceStaticInfo) {
        with(viewBinding) {
            tvPlaceName.text = placeStaticInfo.title
        }
    }

    fun addItem(item: PostPublishingItemView) {
        with(viewBinding) {
            layoutItems.addView(item)
        }
    }
}