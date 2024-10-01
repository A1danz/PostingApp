package com.a1danz.feature_authorization.domain.interactor

import javax.inject.Inject

interface AuthorizationInteractor {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String, name: String)
}