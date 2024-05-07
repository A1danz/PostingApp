package com.a1danz.feature_settings.presentation.screens.social_media.vk

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentVkSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.screens.social_media.vk.rv.VkGroupAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class VkSettingsFragment : BaseFragment(R.layout.fragment_vk_settings) {
    private val viewBinding: FragmentVkSettingsBinding by viewBinding(FragmentVkSettingsBinding::bind)
    private val viewModel: VkSettingsViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        with(viewBinding) {
            lifecycleScope.launch {
                if (viewModel.userHasToken()) {
                    try {
                        updateUiByVkToken()
                    } catch (ex: Exception) {
                        Log.e("ERROR", ex.toString())
                        ex.printStackTrace()
                    }
                } else {
                    layoutNotLinked.isVisible = true
                }
            }

            btnVk.setCallbacks(
                onAuth = { accessToken ->
                    lifecycleScope.launch {
                        val res = viewModel.saveVkToken(accessToken)
                        Log.d("EXPIRED", "${accessToken.expireTime}")
                        if (res) updateUiByVkToken()
                        else Log.e("CANT SAVE", "CANT SAVE VK TOKEN")
                    }
                },
                onFail = { vkidAuthFail ->
                    Log.e("FAILED AUTH", vkidAuthFail.description)
                }
            )
        }
    }

    private suspend fun updateUiByVkToken() {
        println("UPDATED UI")
        with(viewBinding) {
            layoutNotLinked.visibility = View.GONE
            layoutAuthorized.visibility = View.VISIBLE
            val userInfo = viewModel.getUserInfo()
            tvUserName.text = userInfo.fullName
            if (userInfo.userImg != null) {
                Glide.with(requireContext())
                    .load(userInfo.userImg)
                    .into(ivUserImg)
            }
            val groupsUiModels = viewModel.getVkUserGroups()
            rvGroups.adapter = VkGroupAdapter(
                groupsUiModels.groups,
                ::groupChosenCallback
            )

            Log.d("ADAPTER ATTACHED", "ATTACHED - ${rvGroups.adapter}")

            btnUnlink.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.are_you_sure_question))
                    .setMessage(getString(R.string.are_you_sure_what_you_want_unlink_account))
                    .setPositiveButton(
                       getString(R.string.yes)
                    ) { dialog, which ->
                        lifecycleScope.launch {
                            viewModel.unlinkVkUser()
                        }
                    }.setNegativeButton(getString(R.string.no)) { _, _ -> }
                    .show()
            }
        }
    }

    private fun groupChosenCallback(isChosen: Boolean, groupUiModel: VkUserGroupUiModel) {
        lifecycleScope.launch {
            if (isChosen) viewModel.addVkGroup(groupUiModel)
            else viewModel.removeGroup(groupUiModel)
        }
    }
}