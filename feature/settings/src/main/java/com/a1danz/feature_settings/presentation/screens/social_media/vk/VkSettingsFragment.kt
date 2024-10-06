package com.a1danz.feature_settings.presentation.screens.social_media.vk

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentVkSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.presentation.model.state.PlatformScreenState
import com.a1danz.feature_settings.presentation.model.state.VkPlatformScreenState
import com.a1danz.feature_settings.presentation.model.vk.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.vk.VkUserInfoUiModel
import com.a1danz.feature_settings.presentation.screens.social_media.vk.rv.VkGroupAdapter
import com.bumptech.glide.Glide

class VkSettingsFragment : BaseFragment<VkSettingsViewModel>(R.layout.fragment_vk_settings) {

    private val viewBinding: FragmentVkSettingsBinding by viewBinding(FragmentVkSettingsBinding::bind)

    override val viewModel: VkSettingsViewModel by viewModels { vmFactory }

    private val adapter: VkGroupAdapter by lazy {
        VkGroupAdapter(::groupChosenCallback)
    }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        viewModel.screenState.observe(::collectScreenState)
        viewModel.userGroupsState.observe(::collectUserGroupsState)
    }

    override fun initViews() {
        with(viewBinding) {
            viewModel.initScreenState()

            btnVk.setCallbacks(
                onAuth = { accessToken ->
                    viewModel.onAccessTokenReceived(accessToken)
                },
                onFail = { vkIdAuthFail ->
                    viewModel.onAccessTokenFailed(vkIdAuthFail.description)
                }
            )
        }
    }

    private fun groupChosenCallback(groupUiModel: VkUserGroupUiModel, isChosen: Boolean) {
        viewModel.handleVkGroupEdit(groupUiModel, isChosen)
    }

    private fun collectScreenState(state: PlatformScreenState<VkUserInfoUiModel>?) {
        with(viewBinding) {
            state?.let { screenState ->
                layoutAuthorized.isGone = screenState !is PlatformScreenState.Linked
                layoutNotLinked.isGone = screenState !is PlatformScreenState.Unlinked

                when(screenState) {
                    is PlatformScreenState.Linked -> {
                        initLinkedScreen(screenState.platformUserUiInfo)

                    }
                    is PlatformScreenState.Unlinked -> {

                    }
                }
            }
        }
    }

    private fun initLinkedScreen(userInfo: VkUserInfoUiModel) {
        with(viewBinding) {
            viewModel.loadVkUserGroups()
            rvGroups.adapter = adapter

            tvUserName.text = userInfo.name

            userInfo.photo?.let { img ->
                Glide.with(requireContext())
                    .load(img)
                    .into(ivUserImg)
            }

            btnUnlink.setOnClickListener {
                viewModel.onUnlinkBtnClicked()
            }
        }
    }

    private fun collectUserGroupsState(state: List<VkUserGroupUiModel>?) {
        state?.let { userGroups ->
            adapter.setItems(userGroups)
            viewBinding.tvGroupsEmpty.isVisible = userGroups.isEmpty()
        }
    }
}