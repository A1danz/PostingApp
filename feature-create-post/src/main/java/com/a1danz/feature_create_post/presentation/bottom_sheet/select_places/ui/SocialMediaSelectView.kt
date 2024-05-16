package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.a1danz.feature_create_post.databinding.ViewSocialMediaBinding
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.model.PostPlaceUiModel
import com.bumptech.glide.Glide

class SocialMediaSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val viewBinding: ViewSocialMediaBinding = ViewSocialMediaBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    var staticInfo: PostPlaceStaticInfo? = null

    fun setIcon(@DrawableRes drawable: Int?) {
        Glide.with(this)
            .load(drawable)
            .into(viewBinding.ivIcon)
    }

    fun setTitle(title: String) {
        viewBinding.tvTitle.text = title
    }

    fun setAdditionalInfo(text: String) {
        viewBinding.tvAdditionalInfo.text = text
    }

    fun isThisSelected(): Boolean {
        return viewBinding.switchBtn.isSelected
    }

    fun setSelectedState(selected: Boolean) {
        viewBinding.switchBtn.isChecked = selected
    }

    fun setSelectCallback(callback: (Boolean, PostPlaceStaticInfo) -> Unit) {
        viewBinding.switchBtn.setOnCheckedChangeListener { view, isChecked ->
            staticInfo?.let { callback.invoke(isChecked, it) }
        }
    }

    fun setUiModel(postPlace: PostPlaceUiModel) {
        setAdditionalInfo(postPlace.additionalInfo)
        setIcon(postPlace.staticInfo.img)
        setTitle(postPlace.staticInfo.title)
        setSelectedState(postPlace.isSelected)
        staticInfo = postPlace.staticInfo
    }


}