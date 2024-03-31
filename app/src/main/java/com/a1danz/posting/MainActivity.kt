package com.a1danz.posting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.posting.databinding.ActivityMainBinding
import com.a1danz.posting.di.DaggerAppComponent
import com.a1danz.posting.navigation.Navigator
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _viewBinding : ActivityMainBinding? = null
    private val viewBinding : ActivityMainBinding get() = _viewBinding!!

    @Inject lateinit var navigator : Navigator
    @Inject lateinit var authService : AuthorizationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        (application as App).appComponent.inject(this)


        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        navigator.attachNavController(getNavController())
        lifecycleScope.launch {
            if (authService.hasUser().not()) {
                withContext(Dispatchers.Main) {
                    getNavController().navigate(R.id.action_nextFragment_to_signInFragment2)
                }
            }
        }


    }

    fun getNavController() : NavController {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        return navHost.navController
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


}