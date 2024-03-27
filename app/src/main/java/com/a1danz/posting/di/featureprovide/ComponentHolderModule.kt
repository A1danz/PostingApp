package com.a1danz.posting.di.featureprovide

import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.di.holder.AuthorizationFeatureHolder
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
    fun provideAuthFeatureHolder(authFeatureHolder : AuthorizationFeatureHolder) : FeatureApiHolder

}