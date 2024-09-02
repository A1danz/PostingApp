package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewSocialMediaBinding
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.model.PostPlaceUiModel
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.bumptech.glide.Glide

class SocialMediaSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_social_media, this)
    }

    private val viewBinding by viewBinding(ViewSocialMediaBinding::bind)

    private var placeUiInfo: PostPlaceUiInfo? = null

    private fun setIcon(@DrawableRes drawable: Int?) {
        Glide.with(this)
            .load(drawable)
            .into(viewBinding.ivIcon)
    }

    private fun setSelectCallback(callback: (Boolean, PostPlaceUiInfo) -> Unit) {
        viewBinding.switchBtn.setOnCheckedChangeListener { view, isChecked ->
            placeUiInfo?.let { callback.invoke(isChecked, it) }
        }
    }

    fun setupData(postPlace: PostPlaceUiModel, selectCallback: (Boolean, PostPlaceUiInfo) -> Unit) {
        setIcon(postPlace.uiInfo.img)
        setSelectCallback(selectCallback)

        viewBinding.tvTitle.text = context.getString(postPlace.uiInfo.title)
        viewBinding.tvAdditionalInfo.text = postPlace.additionalInfo
        viewBinding.switchBtn.isChecked = postPlace.isSelected

        placeUiInfo = postPlace.uiInfo
    }


}