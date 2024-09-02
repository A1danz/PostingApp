package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewPostPublishingBinding
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo

class PostPublishingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_post_publishing, this)
    }

    private val viewBinding: ViewPostPublishingBinding by viewBinding(ViewPostPublishingBinding::bind)

    fun setPlaceInfo(placeUiInfo: PostPlaceUiInfo) {
        with(viewBinding) {
            tvPlaceName.text = context.getString(placeUiInfo.title)
        }
    }

    fun addPostPublishingItemToLayout(postPublishingItemView: PostPublishingItemView) {
        viewBinding.layoutItems.addView(postPublishingItemView)
    }
}