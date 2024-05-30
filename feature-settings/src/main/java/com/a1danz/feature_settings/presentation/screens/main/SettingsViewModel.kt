package com.a1danz.feature_settings.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.a1danz.common.core.config.InfoAboutOtherApps
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRouter: SettingsRouter
) : ViewModel() {
    fun navigateToSocialMediaSettingsScreen() {
        settingsRouter.openSocialMediaSettings()
    }

    fun navigateToAboutScreen() {
        settingsRouter.openAboutScreen()
    }

    fun openFeedbackLink(context: Context) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(InfoAboutOtherApps.FEEDBACK_LINK))
        startActivity(context, intent, null)
    }
}