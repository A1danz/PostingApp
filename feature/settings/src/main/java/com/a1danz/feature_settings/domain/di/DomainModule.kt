package com.a1danz.feature_settings.domain.di

import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.interactor.impl.UserInteractorImpl
import com.a1danz.feature_settings.domain.interactor.tg.TgUserInteractor
import com.a1danz.feature_settings.domain.interactor.vk.VkUserInteractor
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        InteractorBinderModule::class
    ]
)
class DomainModule

@Module
interface InteractorBinderModule {
    @Binds
    fun bindTgInteractor_to_Impl(impl: UserInteractorImpl): TgUserInteractor

    @Binds
    fun bindVkInteractor_to_Impl(impl: UserInteractorImpl): VkUserInteractor

    @Binds
    fun bindInteractor_to_Impl(impl: UserInteractorImpl): UserInteractor
}