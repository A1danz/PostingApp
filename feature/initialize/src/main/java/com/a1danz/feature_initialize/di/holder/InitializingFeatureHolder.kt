package com.a1danz.feature_initialize.di.holder

import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.di.holder.AuthorizationFeatureHolder
import com.a1danz.feature_initialize.di.DaggerInitializingComponent
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_user_configurer.di.holder.UserConfigurerFeatureHolder
import javax.inject.Inject

@ApplicationScope
class InitializingFeatureHolder @Inject constructor(
    private val router: InitializingRouter,
    private val resourceManager : ResourceManager,
    private val userConfigurerFeatureHolder: UserConfigurerFeatureHolder,
    private val authorizationFeatureHolder: AuthorizationFeatureHolder,
) : FeatureApiHolder() {

    override fun getComponent(): Any {
        return DaggerInitializingComponent.builder()
            .router(router)
            .resManager(resourceManager)
            .authorizationComponent(authorizationFeatureHolder.authorizationComponent)
            .userConfigurerComponent(userConfigurerFeatureHolder.userConfigurerComponent)
            .build()
    }
}