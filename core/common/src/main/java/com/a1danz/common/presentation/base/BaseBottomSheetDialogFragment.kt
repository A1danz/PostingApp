package com.a1danz.common.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment(@LayoutRes layoutRes: Int) : BottomSheetDialogFragment(layoutRes) {
    @Inject
    open lateinit var vmFactory: ViewModelProvider.Factory

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