package com.a1danz.feature_authorization.domain.interactor

interface AuthorizationInteractor {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String, name: String)
}