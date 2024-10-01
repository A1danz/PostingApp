package com.a1danz.feature_authorization.data.exceptionhandler

import com.a1danz.common.delegate.ExceptionHandlerDelegate
import com.a1danz.feature_authorization.domain.exceptions.AuthException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FirebaseExceptionHandlerDelegate : ExceptionHandlerDelegate {
    override fun handleException(ex : Throwable) : Throwable {
        if (ex !is FirebaseException) return ex
        return when(ex) {
            is FirebaseAuthWeakPasswordException -> AuthException.WeakPasswordException()
            is FirebaseAuthInvalidCredentialsException -> AuthException.InvalidCredentialsException()
            is FirebaseAuthInvalidUserException -> AuthException.InvalidUserException()
            is FirebaseAuthUserCollisionException -> AuthException.UserAlreadyExistsException()
            else -> AuthException.UnknownException()
        }
    }

}