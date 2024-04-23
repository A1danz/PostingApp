package com.a1danz.feature_authorization.di

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.presentation.screens.signin.SignInFragment
import com.a1danz.feature_authorization.presentation.screens.signup.SignUpFragment
import dagger.BindsInstance
import dagger.Component


@Component(modules = [AuthModule::class])
@FeatureScope
interface AuthComponent {
    @Component.Builder
    interface Builder {
        fun build(): AuthComponent

        @BindsInstance
        fun router(router: AuthorizationRouter): Builder

        @BindsInstance
        fun resourceManager(resManager: ResourceManager): Builder

        @BindsInstance
        fun authorizationService(authorizationService: AuthorizationService): Builder
    }

    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
}