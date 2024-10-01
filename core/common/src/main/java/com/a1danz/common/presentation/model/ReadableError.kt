package com.a1danz.common.presentation.model

import com.a1danz.common.R

sealed class ReadableError(
    val uiMessage: Text,
    val ex: Exception? = null,
    val exceptionMsg: String? = null,
) {
    class Default(ex: Exception? = null, msg: String? = null) : ReadableError(
        Text.Resource(R.string.error_default_msg)
    )

    class Custom(uiMessage: Text, ex: Exception? = null, exceptionMsg: String? = null)
        : ReadableError(uiMessage, ex, exceptionMsg)
}