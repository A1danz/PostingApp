package com.a1danz.feature_authorization.domain.service

import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidUserException
import com.a1danz.feature_authorization.domain.service.exceptions.UserAlreadyExistsException
import com.a1danz.feature_authorization.domain.service.exceptions.WeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlin.jvm.Throws

interface AuthorizationService {
    @Throws(AuthException::class,
        InvalidCredentialsException::class,
        InvalidUserException::class)
    suspend fun signIn(email: String, password: String)

    @Throws(
        AuthException::class,
        InvalidCredentialsException::class,
        WeakPasswordException::class,
        UserAlreadyExistsException::class
    )
    suspend fun signUp(email : String, password : String, name: String)
    suspend fun signOut()
    suspend fun hasUser() : Boolean
    suspend fun getUser(): FirebaseUser
}