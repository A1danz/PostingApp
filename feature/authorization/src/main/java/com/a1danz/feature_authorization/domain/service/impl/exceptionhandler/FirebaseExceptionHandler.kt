package com.a1danz.feature_authorization.domain.service.impl.exceptionhandler



inline fun <T, R> T.runCatching(
    exceptionHandlerDelegate : FirebaseExceptionHandlerDelegate,
    block : T.() -> R,
) : Result<R> {
    return try {
        Result.success(block())
    } catch (ex : Throwable) {
        Result.failure(exceptionHandlerDelegate.handleException(ex))
    }
}