package com.a1danz.common.presentation.base

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ToastData
import com.a1danz.common.presentation.base.model.event.BaseUiEvent
import com.a1danz.common.presentation.model.ReadableError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val _baseUiEvent: MutableSharedFlow<BaseUiEvent> = MutableSharedFlow()
    val baseUiEvent: SharedFlow<BaseUiEvent> = _baseUiEvent

    protected fun MutableSharedFlow<BaseUiEvent>.emitErrorEvent(readableError: ReadableError) {
        this.emitEvent(BaseUiEvent.ShowError(readableError))
    }

    protected fun MutableSharedFlow<BaseUiEvent>.emitToastEvent(toastData: ToastData) {
        this.emitEvent(BaseUiEvent.ShowToast(toastData))
    }

    protected fun MutableSharedFlow<BaseUiEvent>.emitAlertDialogEvent(alertDialog: AlertDialogData) {
        this.emitEvent(BaseUiEvent.ShowAlertDialog(alertDialog))
    }

    protected fun MutableSharedFlow<BaseUiEvent>.emitShowIntentEvent(intent: Intent) {
        this.emitEvent(BaseUiEvent.ShowIntent(intent))
    }

    fun needToShowError(readableError: ReadableError) {
        _baseUiEvent.emitErrorEvent(readableError)
    }

    fun needToShowToast(toastData: ToastData) {
        _baseUiEvent.emitToastEvent(toastData)
    }

    fun needToShowAlertDialog(alertDialog: AlertDialogData) {
        _baseUiEvent.emitAlertDialogEvent(alertDialog)
    }

    protected fun MutableSharedFlow<BaseUiEvent>.emitEvent(event: BaseUiEvent) {
        viewModelScope.launch {
            this@emitEvent.emit(event)
        }
    }
}