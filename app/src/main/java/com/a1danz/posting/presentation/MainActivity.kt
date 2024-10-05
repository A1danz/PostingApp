package com.a1danz.posting.presentation

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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

    @Inject lateinit var navigator : Navigator

    private var bottomNavigationIsActive = false

    override fun inject() {
        (application as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        navigator.attachNavController(getNavController())

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(BNV_IS_ACTIVE_ARG)) {
                activateBnv()
            }
        }
    }

    override fun activateBnv() {
        viewBinding.bnv.let { bnv ->
            bottomNavigationIsActive = true
            bnv.isVisible = true

            bnv.setupWithNavController(getNavController())

            bnv.setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, getNavController())

                return@setOnItemSelectedListener true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(BNV_IS_ACTIVE_ARG, bottomNavigationIsActive)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detachNavController()
        _viewBinding = null
    }

    companion object {
        private const val BNV_IS_ACTIVE_ARG = "bnv_is_active"
    }
}