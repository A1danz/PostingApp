package com.a1danz.common.presentation.base

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val fragmentContainerId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        inject()
    }

    protected fun getNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(fragmentContainerId) as NavHostFragment
        return navHost.navController
    }

    abstract fun activateBnv()
    abstract fun inject()
}