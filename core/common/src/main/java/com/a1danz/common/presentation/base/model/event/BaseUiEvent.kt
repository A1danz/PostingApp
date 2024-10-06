package com.a1danz.common.presentation.base.model.event

import android.content.Intent
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ToastData
import com.a1danz.common.presentation.model.ReadableError

sealed interface BaseUiEvent {
    data class ShowAlertDialog(val dialogData: AlertDialogData) : BaseUiEvent
    data class ShowToast(val toastData: ToastData) : BaseUiEvent
    data class ShowError(val readableError: ReadableError) : BaseUiEvent
    data class ShowIntent(val intent: Intent) : BaseUiEvent
}