package com.a1danz.posting

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.posting.databinding.ActivityMainBinding
import com.a1danz.posting.di.DaggerAppComponent
import com.a1danz.posting.navigation.Navigator
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _viewBinding : ActivityMainBinding? = null
    private val viewBinding : ActivityMainBinding get() = _viewBinding!!

    @Inject lateinit var navigator : Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        (application as App).appComponent.inject(this)
        navigator.attachNavController(getNavController())
    }

    fun getNavController() : NavController{
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