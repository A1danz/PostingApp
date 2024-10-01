package com.a1danz.common.presentation.base

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.ext.observe
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
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

    fun showAlertDialog(alertDialogData: AlertDialogData) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle(alertDialogData.title)
        alertDialogData.apply {
            if (message != null) dialogBuilder.setMessage(message)
            if (positiveButton != null) dialogBuilder.setPositiveButton(positiveButton.text) { _, _ ->
                positiveButton.callback?.invoke()
            }
            if (negativeButton != null) dialogBuilder.setNegativeButton(negativeButton.text) { _, _ ->
                negativeButton.callback?.invoke()
            }
        }

        dialogBuilder.show()
    }

    fun <T> Flow<T>.observe(block: (T) -> Unit) {
        this.observe(this@BaseBottomSheetDialogFragment, block)
    }

    abstract fun inject()
    abstract fun subscribe()
    abstract fun initViews()
}