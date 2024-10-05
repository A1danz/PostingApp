package com.a1danz.posting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.a1danz.common.presentation.nav.GoBackRouter
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import com.a1danz.posting.R

class Navigator: AuthorizationRouter, SettingsRouter, InitializingRouter, GoBackRouter {
    private var navController : NavController? = null

    fun attachNavController(navController: NavController) {
        this.navController = navController
    }

    fun detachNavController() {
        navController = null
    }

    override fun openSignInScreen() {
        navController?.navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    override fun openSignUpScreen() {
        navController?.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun openMainScreen() {
        navController?.navigate(R.id.initializingFragment)
    }

    override fun openSocialMediaSettings() {
        navController?.navigate(R.id.action_settingsFragment_to_socialMediaSettingsFragment)
    }

    override fun openAboutScreen() {
        navController?.navigate(R.id.action_settingsFragment_to_aboutFragment)
    }

    override fun navigateFromInitializingToAuthorization() {
        navController?.navigate(R.id.action_initializingFragment_to_signInFragment)
    }

    override fun navigateFromInitializingToMain() {
        val primaryScreenId = R.id.createPostFragment
        navController?.graph?.setStartDestination(primaryScreenId)
        navController?.navigate(
            resId = primaryScreenId,
            args = null,
            navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.initializingFragment, true)
                .build()
        )
    }

    override fun openVkSettings() {
        navController?.navigate(R.id.action_socialMediaSettingsFragment_to_vkSettingsFragment)
    }

    override fun openTgSettings() {
        navController?.navigate(R.id.action_socialMediaSettingsFragment_to_tgSettingsFragment)
    }

    override fun goBack() {
        navController?.popBackStack()
    }
}