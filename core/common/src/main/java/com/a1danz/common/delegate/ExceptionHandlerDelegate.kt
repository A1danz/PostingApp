package com.a1danz.common.delegate

import com.google.firebase.FirebaseException

interface ExceptionHandlerDelegate {
    fun handleException(ex : Throwable) : Throwable
}