package com.a1danz.posting.di.navigation

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import com.a1danz.posting.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RouterBinds::class])
class NavigationModule {
    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()
}

@Module
interface RouterBinds {
    @ApplicationScope
    @Binds
    fun bindsNavigator_to_AuthRouter(navigator: Navigator) : AuthorizationRouter

    @ApplicationScope
    @Binds
    fun bindsNavigator_toSettingsRouter(navigator : Navigator): SettingsRouter

    @ApplicationScope
    @Binds
    fun bindsNavigator_to_InitializingRouter(navigator: Navigator): InitializingRouter
}