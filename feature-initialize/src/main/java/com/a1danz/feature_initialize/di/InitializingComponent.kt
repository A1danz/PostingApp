package com.a1danz.feature_initialize.di

import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_initialize.presentation.screens.initialize.InitializingFragment
import com.a1danz.feature_user_configurer.UserConfigurer
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [InitializingModule::class]
)
interface InitializingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun router(router: InitializingRouter): Builder
        @BindsInstance
        fun resManager(resManager: ResourceManager): Builder
        @BindsInstance
        fun authorizationService(authorizationService: AuthorizationService): Builder
        @BindsInstance
        fun userConfigurer(userConfigurer: UserConfigurer): Builder

        fun build(): InitializingComponent
    }

    fun inject(fragment: InitializingFragment)
}