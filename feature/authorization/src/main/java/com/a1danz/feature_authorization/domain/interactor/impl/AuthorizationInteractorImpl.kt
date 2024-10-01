package com.a1danz.feature_authorization.domain.interactor.impl

import com.a1danz.feature_authorization.domain.interactor.AuthorizationInteractor
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import javax.inject.Inject

class AuthorizationInteractorImpl @Inject constructor(
    private val authorizationService: AuthorizationService,
) : AuthorizationInteractor {

    override suspend fun signIn(email: String, password: String) {
        authorizationService.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String, name: String) {
        authorizationService.signUp(email, password, name)
    }
}