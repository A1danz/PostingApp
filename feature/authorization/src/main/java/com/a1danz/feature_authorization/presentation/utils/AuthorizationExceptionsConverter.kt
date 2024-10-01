package com.a1danz.feature_authorization.presentation.utils

import com.a1danz.common.presentation.model.ReadableError
import com.a1danz.common.presentation.model.Text
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.domain.exceptions.AuthException
import javax.inject.Inject

class AuthorizationExceptionsConverter @Inject constructor() {
    fun convertException(ex: Throwable): ReadableError {
        if (ex !is AuthException) return ReadableError.Default()
        return when(ex) {
            is AuthException.InvalidUserException -> ReadableError.Custom(Text.Resource(R.string.user_doest_not_exist))
            is AuthException.InvalidCredentialsException -> ReadableError.Custom(Text.Resource(R.string.invalid_data))
            is AuthException.UnknownException -> ReadableError.Default()
            is AuthException.UserAlreadyExistsException -> ReadableError.Custom(Text.Resource(R.string.user_already_exists))
            is AuthException.WeakPasswordException -> ReadableError.Custom(Text.Resource(R.string.weak_password))
        }
    }
}