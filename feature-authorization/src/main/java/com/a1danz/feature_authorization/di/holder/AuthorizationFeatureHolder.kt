package com.a1danz.feature_authorization.di.holder

import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.di.AuthComponent
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    private val authorizationRouter: AuthorizationRouter
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return AuthComponent.init(authorizationRouter)
    }
}
