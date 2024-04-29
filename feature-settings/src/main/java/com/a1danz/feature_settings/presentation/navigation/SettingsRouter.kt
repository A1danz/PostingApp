package com.a1danz.feature_settings.presentation.navigation

import com.a1danz.feature_settings.presentation.screens.social_media.vk.nav.VkSettingsRouter

interface SettingsRouter : SocialMediaRouter, VkSettingsRouter {
    fun openSocialMediaSettings()
}