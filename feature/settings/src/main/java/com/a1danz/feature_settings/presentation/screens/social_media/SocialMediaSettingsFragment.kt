package com.a1danz.feature_settings.presentation.screens.social_media

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentSocialMediaSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import com.bumptech.glide.Glide


class SocialMediaSettingsFragment : BaseFragment<SocialMediaSettingsViewModel>(R.layout.fragment_social_media_settings) {

    private val viewBinding: FragmentSocialMediaSettingsBinding by viewBinding(FragmentSocialMediaSettingsBinding::bind)

    override val viewModel: SocialMediaSettingsViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        viewModel.vkUserInfoState.observe(::collectVkUserInfoState)
        viewModel.tgUserInfoState.observe(::collectTgUserInfoState)
    }

    override fun initViews() {
        viewModel.initUserInfoStates()

        with(viewBinding) {
            btnTgSettings.setOnClickListener {
                viewModel.navigateToTgSettingsScreen()
            }

            btnVkSettings.setOnClickListener {
                viewModel.navigateToVkSettingsScreen()
            }
        }
    }

    private fun collectTgUserInfoState(tgUserInfo: TgUserInfoUiModel?) {
        with(viewBinding) {
            layoutTgData.isVisible = tgUserInfo != null
            tvTgPleaseDoOuath.isVisible = tgUserInfo == null

            if (tgUserInfo != null) {
                Glide.with(requireContext())
                    .load(tgUserInfo.photo)
                    .into(ivTgImage)

                tvTgUserName.text = getString(R.string.tg_username, tgUserInfo.username)

                tvTgLinkGroups.text = tgUserInfo.chatNames.let { chats ->
                    if (chats.isEmpty()) {
                        getString(R.string.you_havent_select_any_group_yet)
                    } else {
                        chats.joinToString("\n")
                    }
                }
            }
        }
    }

    private fun collectVkUserInfoState(vkUserInfo: VkUserInfoUiModel?) {
        with(viewBinding) {
            layoutVkData.isVisible = vkUserInfo != null
            tvVkPleaseDoOuath.isVisible = vkUserInfo == null

            if (vkUserInfo != null) {
                if (vkUserInfo.photo != null) {
                    Glide.with(requireContext())
                        .load(vkUserInfo.photo)
                        .into(ivVkImage)
                }

                tvVkUserName.text = vkUserInfo.name

                tvVkLinkGroups.text = vkUserInfo.groupNames.let { groups ->
                    if (groups.isEmpty()) {
                        getString(R.string.you_havent_select_any_group_yet)
                    } else {
                        groups.joinToString("\n")
                    }
                }
            }
        }
    }
}