package com.a1danz.feature_settings.presentation.screens.social_media.tg

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.ext.copyTextToClipboard
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.common.presentation.base.model.ToastData
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentTgSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.presentation.model.event.TgSettingsUiEvent
import com.a1danz.feature_settings.presentation.model.state.PlatformScreenState
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.rv.adapter.TgChatsAdapter
import com.bumptech.glide.Glide

class TgSettingsFragment : BaseFragment<TgSettingsViewModel>(R.layout.fragment_tg_settings) {

    private val viewBinding: FragmentTgSettingsBinding by viewBinding(FragmentTgSettingsBinding::bind)

    override val viewModel: TgSettingsViewModel by viewModels { vmFactory }

    private val adapter by lazy {
        TgChatsAdapter(::chatChosenCallback)
    }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        viewModel.screenState.observe(::collectScreenState)
        viewModel.tgChatsState.observe(::collectTgChatsState)
        viewModel.uiEvent.observe(::collectUiEvent)
    }

    override fun initViews() {
        viewModel.initScreenState()
    }

    private fun chatChosenCallback(isSelected: Boolean, chatUiModel: TgChatUiModel) {
        viewModel.handleTgChatEdit(chatUiModel, isSelected)
    }

    private fun initLinkedScreen(tgUserInfoUiModel: TgUserInfoUiModel) {
        with(viewBinding) {
            rvChats.adapter = adapter

            viewModel.loadTgChats()

            tvUsername.text = getString(R.string.tg_username, tgUserInfoUiModel.username)

            Glide.with(requireContext())
                .load(tgUserInfoUiModel.photo)
                .into(ivUserImg)

            btnUnlink.setOnClickListener {
                viewModel.onUnlinkBtnClicked()
            }
        }
    }

    private fun initUnlinkedScreen() {
        with(viewBinding) {
            tvLinkKey.text = viewModel.getLinkedCode().trim()

            clCodeBox.setOnClickListener {
                viewModel.onLinkedCodeBoxClicked(tvLinkKey.text.toString())
            }

            clTgBotBox.setOnClickListener {
                viewModel.onTelegramBotBoxClicked()
            }

            viewModel.subscribeToLinkUpdates()
        }
    }

    private fun collectScreenState(state: PlatformScreenState<TgUserInfoUiModel>?) {
        with(viewBinding) {
            state?.let { screenState ->
                layoutLinked.isGone = state !is PlatformScreenState.Linked
                layoutUnlinked.isGone = state !is PlatformScreenState.Unlinked

                when(screenState) {
                    is PlatformScreenState.Linked -> {
                        initLinkedScreen(screenState.platformUserUiInfo)
                    }
                    is PlatformScreenState.Unlinked -> {
                        initUnlinkedScreen()
                    }
                }
            }
        }
    }

    private fun collectTgChatsState(state: List<TgChatUiModel>?) {
        state?.let { chats ->
            adapter.submitListWithDisableLoading(chats)

            viewBinding.tvChatsEmpty.isVisible = chats.isEmpty()
        }
    }

    private fun collectUiEvent(uiEvent: TgSettingsUiEvent) {
        when(uiEvent) {
            is TgSettingsUiEvent.CopyTextToClipboard -> {
                requireContext().copyTextToClipboard(
                    label = uiEvent.label,
                    text = uiEvent.text
                )

                viewModel.needToShowToast(
                    ToastData(getString(R.string.text_was_succesfully_copied))
                )
            }
        }
    }
}