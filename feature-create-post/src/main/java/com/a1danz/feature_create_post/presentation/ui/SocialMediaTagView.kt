package com.a1danz.feature_create_post.presentation.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
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

   fun setSocialMedia(info: PostPlaceStaticInfo) {
       with(viewBinding) {
           setBackgroundResource(R.drawable.tag_background)
           tvName.text = info.shortTitle
           Glide.with(this@SocialMediaTagView)
               .load(info.transparentIcon)
               .into(ivIcon)
       }

   }
}