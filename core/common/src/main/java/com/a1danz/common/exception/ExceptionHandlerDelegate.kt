package com.a1danz.common.exception

interface ExceptionHandlerDelegate {
    fun handleException(ex : Throwable) : Throwable
}