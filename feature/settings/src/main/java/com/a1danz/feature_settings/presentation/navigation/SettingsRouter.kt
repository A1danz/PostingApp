package com.a1danz.feature_settings.presentation.navigation

import com.a1danz.common.presentation.nav.GoBackRouter

interface SettingsRouter : SocialMediaRouter, GoBackRouter {
    fun openSocialMediaSettings()
    fun openAboutScreen()
}