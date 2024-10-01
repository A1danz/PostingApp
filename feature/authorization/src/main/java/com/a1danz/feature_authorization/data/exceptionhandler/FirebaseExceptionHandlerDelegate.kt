package com.a1danz.feature_authorization.data.exceptionhandler

import android.util.Log
import com.a1danz.common.delegate.ExceptionHandlerDelegate
import com.a1danz.feature_authorization.domain.exceptions.AuthException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class FirebaseExceptionHandlerDelegate @Inject constructor() : ExceptionHandlerDelegate {
    override fun handleException(ex : Throwable) : Throwable {
        Log.e("ERR", "Authorization err", ex)
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