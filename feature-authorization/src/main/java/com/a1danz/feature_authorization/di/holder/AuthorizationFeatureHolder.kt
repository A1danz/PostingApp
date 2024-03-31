package com.a1danz.feature_authorization.di.holder

import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.di.DaggerAuthComponent
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    private val authorizationRouter: AuthorizationRouter,
    private val resourceManager : ResourceManager,
    private val viewModelFactory : ViewModelProvider.Factory
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerAuthComponent.builder()
            .router(authorizationRouter)
            .resourceManager(resourceManager)
            .viewModelFactory(viewModelFactory)
            .build()
    }
}
