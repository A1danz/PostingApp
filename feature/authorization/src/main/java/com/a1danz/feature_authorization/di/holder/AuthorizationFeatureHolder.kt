package com.a1danz.feature_authorization.di.holder

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.di.DaggerAuthComponent
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    private val authorizationRouter: AuthorizationRouter,
    private val resourceManager : ResourceManager,
    private val authorizationService: AuthorizationService
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerAuthComponent.builder()
            .router(authorizationRouter)
            .resourceManager(resourceManager)
            .authorizationService(authorizationService)
            .build()
    }
}
