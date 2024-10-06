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
import com.a1danz.feature_settings.presentation.model.TgUserInfoUiModel
import com.bumptech.glide.Glide


class SocialMediaSettingsFragment : BaseFragment<SocialMediaSettingsViewModel>(R.layout.fragment_social_media_settings) {
    private val viewBinding: FragmentSocialMediaSettingsBinding by viewBinding(FragmentSocialMediaSettingsBinding::bind)
    override val viewModel: SocialMediaSettingsViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        initVkSection()
        initTgSection()
    }

    private fun initVkSection() {
        with(viewBinding) {
            btnVkSettings.setOnClickListener {
                viewModel.openVkSettingsScreen()
            }

            val vkConfig = viewModel.getUserVkConfig()
            if (vkConfig == null) tvVkPleaseDoOuath.isVisible = true
            else showVkInfo(vkConfig)
        }
    }

    private fun showVkInfo(vkConfig: VkConfig) {
        with(viewBinding) {
            layoutVkData.isVisible = true
            val vkInfoUiModel = viewModel.getVkUserInfoUiModel(vkConfig)

            tvVkUserName.text = vkInfoUiModel.vkName
            vkInfoUiModel.vkGroupNames.let {  groups ->
                tvVkLinkGroups.text =
                    if (groups.isEmpty()) getString(R.string.you_havent_select_any_group_yet)
                    else groups.joinToString("\n")
            }

            if (vkInfoUiModel.photo != null) {
                Glide.with(requireContext())
                    .load(vkInfoUiModel.photo)
                    .into(ivVkImage)
            }
        }


    }

    private fun initTgSection() {
        with(viewBinding) {
            val tgConfig = viewModel.getUserTgConfig()
            if (tgConfig == null) {
                tvTgPleaseDoOuath.isVisible = true
            } else {
                showTelegramInfo(viewModel.getTgUserInfoUiModel(tgConfig))
            }
            btnTgSettings.setOnClickListener {
                viewModel.openTgSettingsScreen()
            }
        }
    }


    private fun showTelegramInfo(tgUserInfo: TgUserInfoUiModel) {
        with(viewBinding) {

            tvTgUserName.text = getString(R.string.tg_username, tgUserInfo.username)
            tgUserInfo.chatNames.let { chats ->
                tvTgLinkGroups.text =
                    if (chats.isEmpty()) getString(R.string.you_havent_select_any_group_yet)
                    else tgUserInfo.chatNames.joinToString("\n")
            }

            layoutTgData.isVisible = true

            Glide.with(requireContext())
                .load(tgUserInfo.photo)
                .into(ivTgImage)
        }
    }

}