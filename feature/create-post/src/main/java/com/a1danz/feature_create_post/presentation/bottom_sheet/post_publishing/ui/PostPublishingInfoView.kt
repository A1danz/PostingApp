package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.databinding.ViewPostPublishingInfoBinding
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingStatusUiModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingUiModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.model.PostPlaceUiModel
import com.bumptech.glide.Glide

class PostPublishingInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val viewBinding: ViewPostPublishingInfoBinding by viewBinding(ViewPostPublishingInfoBinding::bind)

    fun setPostPlaceModel(uiModel: PostPlaceUiModel) {
        with(viewBinding) {
            Glide.with(this@PostPublishingInfoView)
                .load(uiModel.staticInfo.img)
                .into(ivIcon)

            tvTitle.text = uiModel.staticInfo.title
            tvAdditionalInfo.text = uiModel.additionalInfo
        }
    }

    fun setPostPublishingStatusModel(uiModel: PostPublishingStatusUiModel) {
        with(viewBinding) {
            tvPublishingStatus.text = uiModel.uiName
        }
    }

    fun setPostPublishingModel(uiModel: PostPublishingUiModel) {
        setPostPlaceModel(uiModel.postPlaceUiModel)
        setPostPublishingStatusModel(uiModel.postPublishingStatusUiModel)
    }
}