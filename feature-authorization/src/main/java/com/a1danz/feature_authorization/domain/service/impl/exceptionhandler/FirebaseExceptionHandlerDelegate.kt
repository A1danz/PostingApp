package com.a1danz.feature_authorization.domain.service.impl.exceptionhandler

import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidUserException
import com.a1danz.feature_authorization.domain.service.exceptions.UserAlreadyExistsException
import com.a1danz.feature_authorization.domain.service.exceptions.WeakPasswordException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FirebaseExceptionHandlerDelegate {
    fun handleException(ex : Throwable) : Throwable {
        println("HANDLED EXCEPTION - $ex")
        if (ex !is FirebaseException) return ex
        return when(ex) {
            is FirebaseAuthWeakPasswordException -> WeakPasswordException()
            is FirebaseAuthInvalidCredentialsException -> InvalidCredentialsException()
            is FirebaseAuthInvalidUserException -> InvalidUserException()
            is FirebaseAuthUserCollisionException -> UserAlreadyExistsException()
            else -> AuthException()
        }
    }

}