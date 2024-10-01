package com.a1danz.feature_authorization.domain.exceptions

sealed class AuthException : Exception() {
    class UnknownException : AuthException()
    class InvalidCredentialsException : AuthException()
    class InvalidUserException : AuthException()
    class UserAlreadyExistsException : AuthException()
    class WeakPasswordException : AuthException()
}