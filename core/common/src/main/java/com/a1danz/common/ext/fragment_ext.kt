package com.a1danz.common.ext

import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a1danz.common.R
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.common.presentation.base.model.ToastData
import com.a1danz.common.presentation.model.ReadableError

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