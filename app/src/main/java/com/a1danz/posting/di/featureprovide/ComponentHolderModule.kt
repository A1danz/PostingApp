package com.a1danz.posting.di.featureprovide

import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.di.holder.AuthorizationFeatureHolder
import com.a1danz.feature_initialize.di.InitializingComponent
import com.a1danz.feature_initialize.di.holder.InitializingFeatureHolder
import com.a1danz.feature_settings.di.SettingsComponent
import com.a1danz.feature_settings.di.holder.SettingsFeatureHolder
import com.a1danz.feature_user_configurer.di.UserConfigurerComponent
import com.a1danz.feature_user_configurer.di.holder.UserConfigurerFeatureHolder
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ComponentHolderModule {
    @ApplicationScope
    @Binds
    @ClassKey(AuthComponent::class)
    @IntoMap
    fun provideAuthFeatureHolder(authFeatureHolder: AuthorizationFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(SettingsComponent::class)
    @IntoMap
    fun provideSettingsFeatureHolder(settingFeatureHolder: SettingsFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(InitializingComponent::class)
    @IntoMap
    fun provideInitializingFeatureHolder(initializingFeatureHolder: InitializingFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(UserConfigurerComponent::class)
    @IntoMap
    fun provideUserConfigurerFeatureHolder(userConfigurerFeatureHolder: UserConfigurerFeatureHolder): FeatureApiHolder
}