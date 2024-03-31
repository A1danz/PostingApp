package com.a1danz.feature_authorization.domain.service

import com.a1danz.feature_authorization.domain.model.User
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidUserException
import com.a1danz.feature_authorization.domain.service.exceptions.UserAlreadyExistsException
import com.a1danz.feature_authorization.domain.service.exceptions.WeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

interface AuthorizationService {
    val currentUser : Flow<User?>

    @Throws(AuthException::class, InvalidCredentialsException::class, InvalidUserException::class)
    suspend fun signIn(email: String, password: String)

    @Throws(
        AuthException::class, InvalidCredentialsException::class,
        WeakPasswordException::class, UserAlreadyExistsException::class
    )
    suspend fun signUp(email : String, password : String)
    suspend fun signOut()
    suspend fun hasUser() : Boolean
}