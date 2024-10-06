package com.a1danz.common.exception

import com.a1danz.common.presentation.model.ReadableError

interface ExceptionConverter {
    fun convertException(ex: Throwable): ReadableError
}