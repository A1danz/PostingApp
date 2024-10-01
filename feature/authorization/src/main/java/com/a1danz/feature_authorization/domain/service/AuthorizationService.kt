package com.a1danz.feature_authorization.domain.service

import com.a1danz.feature_authorization.domain.exceptions.AuthException
import com.a1danz.feature_authorization.domain.model.AuthorizedUser

interface AuthorizationService {
    /**
     * @throws AuthException.UnknownException base authorization exception
     * @throws AuthException.InvalidCredentialsException if credentials are invalid
     * @throws AuthException.InvalidUserException if user not found
     */
    suspend fun signIn(email: String, password: String)
    /**
     * @throws AuthException.UnknownException base authorization exception
     * @throws AuthException.InvalidCredentialsException if the credentials are invalid
     * @throws AuthException.WeakPasswordException if the password is too easy
     * @throws AuthException.UserAlreadyExistsException if user already exists in system
     */
    suspend fun signUp(email : String, password : String, name: String)
    suspend fun signOut()
    suspend fun hasUser() : Boolean
    suspend fun getUser(): AuthorizedUser?
}