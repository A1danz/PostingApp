package com.a1danz.feature_settings.presentation.screens.social_media

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentSocialMediaSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.presentation.ui.VkGroupAdapter
import com.a1danz.feature_settings.presentation.view.FacebookBtn
import com.bumptech.glide.Glide
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.launch
import javax.inject.Inject


class SocialMediaSettingsFragment : BaseFragment(R.layout.fragment_social_media_settings) {
    private val viewBinding: FragmentSocialMediaSettingsBinding by viewBinding(FragmentSocialMediaSettingsBinding::bind)
    private val viewModel: SocialMediaSettingsViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        with(viewBinding) {
            btnVkSettings.setOnClickListener {
                viewModel.openVkSettingsScreen()
            }

            btnTgSettings.setOnClickListener {
                viewModel.openTgSettingsScreen()
            }

            initVkSection()
        }
    }

    private fun initVkSection() {
        with(viewBinding) {
            lifecycleScope.launch {
                if (viewModel.userHasVkToken()) {
                    layoutVkData.isVisible = true

                    val groups = viewModel.getUserSelectedVkGroups()
                    val vkUserInfo = viewModel.getUserInfo()

                    Glide.with(requireContext())
                        .load(vkUserInfo.userImg)
                        .into(ivVkImage)

                    tvVkUserName.text = vkUserInfo.fullName
                    tvVkLinkGroups.text = if (groups.isNotEmpty()) (groups.map { it.groupName }).joinToString("\n")
                                        else "Нет связанных групп"
                } else {
                    tvPleaseDoOuath.isVisible = true
                }
            }
        }

    }

}