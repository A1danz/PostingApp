package com.a1danz.common.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.featureprovide.FeatureContainer
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

    @Inject open lateinit var vmFactory : ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        initViews()
    }

    abstract fun inject()
    abstract fun subscribe()
    abstract fun initViews()
}