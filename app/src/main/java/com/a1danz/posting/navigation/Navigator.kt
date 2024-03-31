package com.a1danz.posting.navigation

import androidx.navigation.NavController
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.posting.R
import javax.inject.Inject

class Navigator: AuthorizationRouter {
    var navController : NavController? = null

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
        navController?.navigate(R.id.nextFragment)
    }
}