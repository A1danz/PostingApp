package com.a1danz.feature_settings.presentation.screens.main

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent

class SettingsFragment : BaseFragment<SettingsViewModel>(R.layout.fragment_settings) {
    private val viewBinding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    override val viewModel: SettingsViewModel by viewModels { vmFactory }
    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(SettingsComponent::class.java)
            ?.inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        with(viewBinding) {
            itemSocialMediaSettings.setOnClickListener {
                viewModel.navigateToSocialMediaSettingsScreen()
            }
            itemAbout.setOnClickListener {
                viewModel.navigateToAboutScreen()
            }
            itemFeedback.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.feedback))
                    .setMessage(getString(R.string.feedback_description))
                    .setPositiveButton(getString(R.string.open)) { dialog, _ ->
                        viewModel.openFeedbackLink(requireContext())
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> }
                    .show()
            }
        }
    }
}