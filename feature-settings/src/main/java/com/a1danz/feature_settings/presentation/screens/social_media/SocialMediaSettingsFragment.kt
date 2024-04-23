package com.a1danz.feature_settings.presentation.screens.social_media

import android.util.Log
import android.view.View
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
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.launch
import javax.inject.Inject


class SocialMediaSettingsFragment : BaseFragment(R.layout.fragment_social_media_settings) {
    private val viewBinding: FragmentSocialMediaSettingsBinding by viewBinding(FragmentSocialMediaSettingsBinding::bind)

    @Inject lateinit var callbackManager: CallbackManager
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
            // vk
            lifecycleScope.launch {
                Log.d("UPDATING", "UPDATE UI IN START")
                if (viewModel.userHasToken()) updateUiByVkToken()
                else btnVk.isVisible = true
            }

            btnVk.setCallbacks(
                onAuth = { accessToken ->
                    lifecycleScope.launch {
                        val res = viewModel.saveVkToken(accessToken)
                        if (res) {
                            Log.d("UPDATING", "UPDATE UI IN CALLBACK")
                            btnVk.visibility = View.GONE
                            updateUiByVkToken()
                        }
                    }
                },
                onFail = { fail ->
                    Log.e("VKFail", "FAILED AUTH - $fail: ${fail.description}")
                }
            )

            (requireActivity().application as FeatureContainer)
                .getFeature(SettingsComponent::class.java)
                .inject(btnFacebook)

            subscribeToFacebookUpdates(btnFacebook)
        }
    }

    private fun updateUiByVkToken() {
        with(viewBinding) {
            btnVk.visibility = View.GONE
            tvVkSuccess.visibility = View.VISIBLE
            lifecycleScope.launch {
                viewModel.getVkUserGroups().collect {groupsModel ->
                    groupsModel?.let {
                        val groups = it.groups
                        val adapter = VkGroupAdapter(requireContext(), groups)
                        spinner.adapter = adapter
//                        val groupNames = groups.map { group -> group.name }
//                        val arrayAdapter =
//                            ArrayAdapter(requireContext(), R.layout.group_dropdown_item, groupNames)
//                        vkGroupsActv.setAdapter(arrayAdapter)
//                        groups.forEach {group ->
//                            Log.d("GROUP", group.toString())
//                        }
                    }
                }
            }
        }
    }

    private fun subscribeToFacebookUpdates(facebookBtn: FacebookBtn) {
        facebookBtn.attachFragment(this)
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken
                    Log.d("FACEBOOK_TOKEN", accessToken.token)
                }

                override fun onCancel() {
                    Log.d("FACEBOOK_TOKEN", "CANCELLED")
                }

                override fun onError(exception: FacebookException) {
                    Log.d("FACEBOOK_TOKEN", "HANDLED EXCEPTION: $exception")
                }
            })
    }

}