package com.a1danz.feature_settings.presentation.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.feature_settings.databinding.FragmentSettingsBinding
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import javax.inject.Inject

class SettingsFragment : Fragment() {
    var _viewBinding : FragmentSettingsBinding? = null
    val viewBinding : FragmentSettingsBinding get() = _viewBinding!!

    @Inject lateinit var settingsRouter: SettingsRouter
    override fun onAttach(context: Context) {
        (requireActivity().application as FeatureContainer).getFeature(SettingsComponent::class.java)
            .inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSettingsBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun initViews() {
        with(viewBinding) {
            itemSocialMediaSettings.setOnClickListener {
                settingsRouter.openSocialMediaSettings()
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}