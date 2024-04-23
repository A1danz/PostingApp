package com.a1danz.feature_settings.di.module.social_media.facebook

import com.a1danz.common.di.scope.FeatureScope
import com.facebook.CallbackManager
import dagger.Module
import dagger.Provides

@Module
class FacebookModule {
    @Provides
    @FeatureScope
    fun provideCallbackManager() : CallbackManager = CallbackManager.Factory.create()
}