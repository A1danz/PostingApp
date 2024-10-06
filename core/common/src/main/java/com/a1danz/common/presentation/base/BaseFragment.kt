package com.a1danz.common.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.ext.observe
import com.a1danz.common.ext.observeBaseUiEvent
import com.a1danz.common.ext.showAlertDialog
import com.a1danz.common.ext.showError
import com.a1danz.common.ext.showToast
import com.a1danz.common.presentation.base.model.event.BaseUiEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel>(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

    @Inject open lateinit var vmFactory : ViewModelProvider.Factory

    protected abstract val viewModel: T

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseSubscribe()
        subscribe()
        initViews()
    }

    private fun baseSubscribe() {
        observeBaseUiEvent(viewModel.baseUiEvent)
    }

    fun <T> Flow<T>.observe(block: (T) -> Unit) {
        this.observe(this@BaseFragment, block)
    }

    abstract fun inject()
    abstract fun subscribe()
    abstract fun initViews()
}