package com.a1danz.posting.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.NavigationUI
import com.a1danz.common.presentation.base.BaseActivity
import com.a1danz.posting.App
import com.a1danz.posting.R
import com.a1danz.posting.databinding.ActivityMainBinding
import com.a1danz.posting.navigation.Navigator
import javax.inject.Inject

class MainActivity : BaseActivity() {
    override val fragmentContainerId: Int = R.id.main_fragment_container

    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding: ActivityMainBinding get() = _viewBinding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory

    @Inject lateinit var navigator : Navigator

    override fun inject() {
        (application as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        navigator.attachNavController(getNavController())
    }

    override fun activateBnv() {
        viewBinding.bnv.let { bnv ->
            bnv.isVisible = true
            bnv.setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, getNavController())

                return@setOnItemSelectedListener true
            }
            navigator.navigateToMainScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detachNavController()
        _viewBinding = null
    }
}