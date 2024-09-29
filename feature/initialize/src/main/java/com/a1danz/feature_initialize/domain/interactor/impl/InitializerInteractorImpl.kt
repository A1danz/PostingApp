package com.a1danz.feature_initialize.domain.interactor.impl

import com.a1danz.feature_authorization.domain.model.AuthorizedUser
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_initialize.domain.interactor.InitializerInteractor
import com.a1danz.feature_user_configurer.UserConfigurer
import javax.inject.Inject

class InitializerInteractorImpl @Inject constructor(
    private val userConfigurer: UserConfigurer,
    private val authorizationService: AuthorizationService
) : InitializerInteractor{

    override suspend fun initializeUser(user: AuthorizedUser) {
        userConfigurer.updateUserDelegate(user.id)
        userConfigurer.initUser()
    }

    override suspend fun getAuthorizedUser(): AuthorizedUser? {
        return authorizationService.getUser()
    }
}
