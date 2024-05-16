package com.a1danz.feature_create_post.presentation.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewSocialMediaBinding
import com.a1danz.feature_create_post.databinding.ViewTagSocialMediaBinding
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.bumptech.glide.Glide

class SocialMediaTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val viewBinding: ViewTagSocialMediaBinding = ViewTagSocialMediaBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private var staticInfo: PostPlaceStaticInfo? = null


    fun setStaticInfo(info: PostPlaceStaticInfo) {
        staticInfo = info
        tag = info.placeType
        with(viewBinding) {
            setBackgroundResource(R.drawable.tag_background)
            tvName.text = info.shortTitle
            Glide.with(this@SocialMediaTagView)
                .load(info.transparentIcon)
                .into(ivIcon)
        }
    }

    fun setEmptyTag() {
        tag = EMPTY_TAG
        with(viewBinding) {
            background = ContextCompat.getDrawable(context, R.drawable.empty_tag_background)
            tvName.text = context.getString(R.string.nothing_selected)
            Glide.with(this@SocialMediaTagView)
                .load(EMPTY_ICON)
                .into(ivIcon)
        }
    }

    companion object {
        const val EMPTY_TAG = "EMPTY_TAG_VIEW"
        @DrawableRes val EMPTY_ICON = R.drawable.empty_icon
        @DrawableRes val EMPTY_TAG_BACKGROUND = R.drawable.empty_tag_background
    }
}