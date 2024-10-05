package com.a1danz.common.ext

import android.util.Log
import com.a1danz.common.delegate.ExceptionHandlerDelegate

inline fun <T, R> T.runCatching(
    exceptionHandlerDelegate : ExceptionHandlerDelegate,
    block : T.() -> R,
) : Result<R> {
    return try {
        Result.success(block())
    } catch (ex: Throwable) {
        Result.failure(exceptionHandlerDelegate.handleException(ex))
    }
}

suspend fun <T, R> T.doOrThrow(
    exceptionHandlerDelegate : ExceptionHandlerDelegate,
    block: suspend T.() -> R,
) : R {
    return try {
        block()
    } catch (ex: Throwable) {
        throw exceptionHandlerDelegate.handleException(ex)
    }
}

suspend fun <T, R> T.doOrLog(
    logMessage: String = "",
    block: suspend T.() -> R,
) : R? {
    return try {
        block()
    } catch (ex: Throwable) {
        Log.e("ERR", logMessage, ex).let {
            null
        }
    }
}