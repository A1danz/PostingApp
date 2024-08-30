package com.a1danz.feature_settings.di.module.social_media

import com.a1danz.feature_settings.di.module.social_media.tg.TgModule
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.interactor.impl.UserInteractorImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        UserInteractorBinder::class,
        TgModule::class
    ]
)
class SocialMediaModule

@Module
interface UserInteractorBinder {
    @Binds
    fun bindImpl_to_UserInteractor(userInteractorImpl: UserInteractorImpl): UserInteractor
}