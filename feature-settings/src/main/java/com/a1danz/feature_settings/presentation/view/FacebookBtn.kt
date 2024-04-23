package com.a1danz.feature_settings.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.a1danz.feature_settings.databinding.FacebookBtnBinding
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginManager
import org.json.JSONObject
import java.util.Arrays
import javax.inject.Inject


class FacebookBtn(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {
    private val viewBinding: FacebookBtnBinding = FacebookBtnBinding.inflate(LayoutInflater.from(context), this, true)
    private var fragment : Fragment? = null
    @Inject lateinit var callbackManager : CallbackManager
    private val permList = listOf("ads_management", "business_management", "pages_read_engagement", "instagram_basic", "instagram_content_publish", "pages_show_list")
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        startFadeInAnimation()
        setOnClickListener {
            authWithFacebook()
        }
    }

    private fun startFadeInAnimation() {
        with(viewBinding) {
            val fadeInAnimation = AlphaAnimation(0f, 1f)
            fadeInAnimation.duration = 1000
            tvTitle.startAnimation(fadeInAnimation)
            ivIcon.startAnimation(fadeInAnimation)
        }

    }

    private fun authWithFacebook() {
        fragment?.let {
            LoginManager.getInstance().logInWithReadPermissions(it, callbackManager, listOf("email"));

        }
    }

    fun attachFragment(frag : Fragment) {
        fragment = frag
    }
}