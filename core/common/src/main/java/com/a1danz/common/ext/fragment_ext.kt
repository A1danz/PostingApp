package com.a1danz.common.ext

import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a1danz.common.R
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.common.presentation.base.model.ToastData
import com.a1danz.common.presentation.base.model.event.BaseUiEvent
import com.a1danz.common.presentation.model.ReadableError
import kotlinx.coroutines.flow.SharedFlow

internal fun Fragment.showAlertDialog(alertDialogData: AlertDialogData) {
    val dialogBuilder = AlertDialog.Builder(requireContext())
        .setTitle(alertDialogData.title)

    alertDialogData.run {
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

internal fun Fragment.showError(error: ReadableError) {
    showAlertDialog(
        AlertDialogData(
            title = getString(R.string.error_default_title),
            message = error.uiMessage.getString(requireContext()),
            positiveButton = ButtonData(getString(R.string.ok))
        )
    )
}

internal fun Fragment.showToast(toastData: ToastData) {
    Toast.makeText(requireContext(), toastData.text, toastData.duration)
        .show()
}

internal fun Fragment.observeBaseUiEvent(uiEvent: SharedFlow<BaseUiEvent>) {
    uiEvent.observe(this.viewLifecycleOwner) { event ->
        when(event) {
            is BaseUiEvent.ShowAlertDialog -> {
                showAlertDialog(event.dialogData)
            }
            is BaseUiEvent.ShowError -> {
                showError(event.readableError)
            }
            is BaseUiEvent.ShowToast -> {
                showToast(event.toastData)
            }

            is BaseUiEvent.ShowIntent -> {
                if (event.intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(event.intent)
                } else {
                    Log.e("INTENT ERR", "Attempt to open intent, but activity not resolved. Intent - ${event.intent}")
                }
            }
        }
    }
}