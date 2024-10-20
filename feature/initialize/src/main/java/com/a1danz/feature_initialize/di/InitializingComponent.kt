package com.a1danz.feature_initialize.di

import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_initialize.presentation.screens.initialize.InitializingFragment
import com.a1danz.feature_user_configurer.di.UserConfigurerComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        InitializingModule::class
    ],
    dependencies = [
        AuthComponent::class,
        UserConfigurerComponent::class,
    ]
)
@FeatureScope
interface InitializingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun router(router: InitializingRouter): Builder

        @BindsInstance
        fun resManager(resManager: ResourceManager): Builder

        fun userConfigurerComponent(userConfigurerComponent: UserConfigurerComponent): Builder

        fun authorizationComponent(authorizationComponent: AuthComponent): Builder

        fun build(): InitializingComponent
    }

    fun inject(fragment: InitializingFragment)
}