package com.a1danz.common.presentation.base.model

data class AlertDialogData(
    val title: String,
    val message: String? = null,
    val positiveButton: ButtonData? = null,
    val negativeButton: ButtonData? = null,
)

data class ButtonData(
    val text: String,
    val callback: (() -> Unit)? = null
)