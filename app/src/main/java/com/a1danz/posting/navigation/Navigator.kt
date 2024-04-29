package com.a1danz.posting.navigation

import androidx.navigation.NavController
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import com.a1danz.posting.R

class Navigator: AuthorizationRouter, SettingsRouter, InitializingRouter {
    private var navController : NavController? = null

    fun attachNavController(navController: NavController) {
        this.navController = navController
    }

    fun detachNavController() {
        navController = null
    }

    override fun openSignInScreen() {
        navController?.navigate(R.id.action_signUpFragment_to_signInFragment2)
    }

    override fun openSignUpScreen() {
        navController?.navigate(R.id.action_signInFragment2_to_signUpFragment)
    }

    override fun openMainScreen() {
        navController?.navigate(R.id.initializingFragment)
    }

    fun navigateToMainScreen() {
        navController?.navigate(R.id.action_initializingFragment_to_settingsFragment)
    }

    override fun openSocialMediaSettings() {
        navController?.navigate(R.id.action_settingsFragment_to_socialMediaSettingsFragment)
    }

    override fun navigateFromInitializingToAuthorization() {
        navController?.navigate(R.id.action_initializingFragment_to_signInFragment2)
    }

    override fun openVkSettings() {
        navController?.navigate(R.id.action_socialMediaSettingsFragment_to_vkSettingsFragment)
    }

    override fun goBack() {
        navController?.popBackStack()
    }
}