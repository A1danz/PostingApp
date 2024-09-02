package com.a1danz.feature_create_post.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewTagSocialMediaBinding
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.a1danz.feature_places_info.presentation.model.toPostPlaceType
import com.bumptech.glide.Glide

class SocialMediaTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_tag_social_media, this)
    }

    private val viewBinding: ViewTagSocialMediaBinding by viewBinding(ViewTagSocialMediaBinding::bind)

    fun setPlaceUiInfo(info: PostPlaceUiInfo) {
        tag = info.javaClass
        with(viewBinding) {
            Glide.with(this@SocialMediaTagView)
                .load(info.transparentIcon)
                .into(ivIcon)

            setBackgroundResource(R.drawable.tag_background)
            tvName.text = context.getString(info.shortTitle)

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
    }
}