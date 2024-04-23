package com.a1danz.feature_settings.di.module.social_media

import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_settings.di.module.social_media.facebook.FacebookModule
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.interactor.impl.UserInteractorImpl
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.impl.UserConfigurerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        FacebookModule::class,
        UserInteractorBinder::class
    ]
)
class SocialMediaModule

@Module
interface UserInteractorBinder {
    @Binds
    fun bindImpl_to_UserInteractor(userInteractorImpl: UserInteractorImpl): UserInteractor
}