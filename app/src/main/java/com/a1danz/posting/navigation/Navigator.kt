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
        navController?.navigate(R.id.signInFragment2)
    }

    override fun openSignUpScreen() {
        navController?.navigate(R.id.signUpFragment)
    }
}