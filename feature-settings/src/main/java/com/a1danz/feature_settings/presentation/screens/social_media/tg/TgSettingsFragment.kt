package com.a1danz.feature_settings.presentation.screens.social_media.tg

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.core.config.InfoAboutOtherApps
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentTgSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.domain.model.TgChatDomainModel
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.a1danz.feature_settings.presentation.screens.social_media.tg.rv.TgChatsAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TgSettingsFragment : BaseFragment(R.layout.fragment_tg_settings) {

    private val viewBinding: FragmentTgSettingsBinding by viewBinding(FragmentTgSettingsBinding::bind)
    private val viewModel: TgSettingsViewModel by viewModels { vmFactory }
    private var clipboardManager: ClipboardManager? = null
    private var copyToClipboardToast: Toast? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        copyToClipboardToast = Toast.makeText(requireContext(),
            getString(R.string.text_was_succesfully_copied), Toast.LENGTH_SHORT)
    }


    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.tgConfigInitializedFlow.collect {
                if (it == true) {
                    showLinkedScreen()
                }
            }
        }
    }

    override fun initViews() {
        lifecycleScope.launch {
            if (viewModel.hasUserTgConfig()) showLinkedScreen()
            else showUnlinkedScreen()
        }
    }

    private fun showLinkedScreen() {
        with(viewBinding) {
            layoutLinked.isVisible = true
            lifecycleScope.launch {
                val chats = viewModel.getChats().chats
                Log.d("CHATS", chats.toString())
                rvChats.adapter = TgChatsAdapter(chats = chats, ::chatChosenCallback)
                if (chats.isEmpty()) {
                    tvChatsEmpty.visibility = View.VISIBLE
                }
            }
            layoutUnlinked.visibility = View.GONE
            val tgUserInfo = viewModel.getTgUserInfo()
            tvUsername.text = getString(R.string.tg_username, tgUserInfo.tgUsername)
            Glide.with(requireContext())
                .load(tgUserInfo.tgUserPhoto)
                .into(ivUserImg)

            btnUnlink.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.are_you_sure_question))
                    .setMessage(getString(R.string.are_you_sure_what_you_want_unlink_account))
                    .setPositiveButton(
                        getString(R.string.yes)
                    ) { dialog, which ->
                        lifecycleScope.launch {
                            viewModel.unlinkTg()
                        }
                    }.setNegativeButton(getString(R.string.no)) { _, _ -> }
                    .show()
            }
        }
    }

    private fun chatChosenCallback(isSelected: Boolean, chatUiModel: TgChatUiModel) {
        Log.d("CALLBACK FRAGMENT", "$isSelected ${chatUiModel.chatId}")
        lifecycleScope.launch {
            if (isSelected) viewModel.addSelectedChat(chatUiModel)
            else viewModel.removeSelectedChat(chatUiModel)
        }
    }

    private fun showUnlinkedScreen() {
        with(viewBinding) {
            layoutUnlinked.isVisible = true
            layoutLinked.visibility = View.GONE
            tvLinkKey.text = viewModel.getLinkedCode().trim()
            Log.d("KEY", tvLinkKey.text.toString().trim())

            clCodeBox.setOnClickListener {
                copyToClipboard(getString(R.string.posting_app_code), tvLinkKey.text.toString())
            }

            clTgBotBox.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(InfoAboutOtherApps.TG_DEEPLINK_TO_CHAT + "posting_app_bot"))

                try {
                    startActivity(intent)
                } catch (ex: Exception) {
                    copyToClipboard(getString(R.string.posting_bot), tvBotUsername.text.toString())
                }
            }
            subscribeToLinkUpdates()
        }
    }

    private fun subscribeToLinkUpdates() {
        lifecycleScope.launch {
            viewModel.subscribeToLinkUpdates()
        }
    }

    private fun copyToClipboard(label: String, text: String) {
        clipboardManager?.let {
            it.setPrimaryClip(ClipData.newPlainText(
                label, text
            ))

            copyToClipboardToast?.show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        clipboardManager = null
        copyToClipboardToast = null
    }
}