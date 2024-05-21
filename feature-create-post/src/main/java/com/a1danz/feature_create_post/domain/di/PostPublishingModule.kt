package com.a1danz.feature_create_post.domain.di

import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.User
import com.a1danz.feature_create_post.domain.interactor.DatastoreUserInteractor
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.interactor.impl.DataStoreUserInteractorImpl
import com.a1danz.feature_create_post.domain.interactor.impl.UserInteractorImpl
import com.a1danz.feature_telegram_publisher.di.DaggerTelegramPublisherComponent
import com.a1danz.feature_telegram_publisher.di.TelegramPublisherComponent
import com.a1danz.feature_telegram_publisher.domain.TelegramPublisher
import com.a1danz.vk_publisher.di.DaggerVkPublisherComponent
import com.a1danz.vk_publisher.di.VkPublisherComponent
import com.suke.widget.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [
    UserInteractorBinderModule::class,
    DataStoreInteractorBinderModule::class
])
class PostPublishingModule {

    @Provides
    fun providesTelegramPublisherComponent(): TelegramPublisherComponent = DaggerTelegramPublisherComponent.builder()
        .botToken("tg_bot_key")
        .build()

    @Provides
    fun provideVkPublisherComponent(user: User): VkPublisherComponent = DaggerVkPublisherComponent.builder()
        .vkAccessToken(user.config.vkConfig?.accessToken ?: "TOKEN doesn't initialized")
        .build()
}

@Module
interface UserInteractorBinderModule {

    @Binds
    fun bindUserInteractor_to_Impl(userInteractorImpl: UserInteractorImpl): UserInteractor
}

@Module
interface DataStoreInteractorBinderModule {

    @Binds
    fun bindDataStoreInteractor_to_Impl(dataStoreUserInteractorImpl: DataStoreUserInteractorImpl): DatastoreUserInteractor
}