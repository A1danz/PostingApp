package com.a1danz.feature_settings.presentation.screens.about

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {
    val viewBinding: FragmentAboutBinding by viewBinding(FragmentAboutBinding::bind)
}