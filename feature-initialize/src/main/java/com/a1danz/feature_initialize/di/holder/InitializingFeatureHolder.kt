package com.a1danz.feature_initialize.di.holder

import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_initialize.di.DaggerInitializingComponent
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.di.UserConfigurerComponent
import com.a1danz.feature_user_configurer.di.holder.UserConfigurerFeatureHolder
import javax.inject.Inject

class InitializingFeatureHolder @Inject constructor(
    private val router: InitializingRouter,
    private val authorizationService: AuthorizationService,
    private val resourceManager : ResourceManager,
    private val userConfHolder: UserConfigurerFeatureHolder
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerInitializingComponent.builder()
            .router(router)
            .authorizationService(authorizationService)
            .resManager(resourceManager)
            .userConfigurer(
                (userConfHolder.getComponent() as? UserConfigurerComponent)
                    ?.getUserConfigurer() ?: throw IllegalStateException("Can't cast UserConfigurerComponent in InitFeatureHolder")
            )
            .build()
    }
}