package com.a1danz.feature_initialize.domain.interactor

import com.a1danz.feature_authorization.domain.model.AuthorizedUser

interface InitializerInteractor {
    suspend fun initializeUser(user: AuthorizedUser)
    suspend fun getAuthorizedUser(): AuthorizedUser?
}