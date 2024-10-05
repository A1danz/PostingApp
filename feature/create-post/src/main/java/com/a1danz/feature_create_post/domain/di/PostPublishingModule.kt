package com.a1danz.feature_create_post.domain.di

import com.a1danz.common.domain.model.User
import com.a1danz.feature_create_post.BuildConfig
import com.a1danz.feature_create_post.domain.interactor.CreatePostInteractor
import com.a1danz.feature_create_post.domain.interactor.PostPublishingInteractor
import com.a1danz.feature_create_post.domain.interactor.impl.CreatePostInteractorImpl
import com.a1danz.feature_create_post.domain.interactor.impl.PostPublishingInteractorImpl
import com.a1danz.feature_telegram_publisher.di.DaggerTelegramPublisherComponent
import com.a1danz.feature_telegram_publisher.di.TelegramPublisherComponent
import com.a1danz.vk_publisher.di.DaggerVkPublisherComponent
import com.a1danz.vk_publisher.di.VkPublisherComponent
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [
    PostPublishingInteractorBinderModule::class
])
class PostPublishingModule {

    @Provides
    fun providesTelegramPublisherComponent(): TelegramPublisherComponent = DaggerTelegramPublisherComponent.builder()
        .botToken(BuildConfig.TG_BOT_TOKEN)
        .build()

    @Provides
    fun provideVkPublisherComponent(user: User): VkPublisherComponent = DaggerVkPublisherComponent.builder()
        .vkAccessToken(user.config.vkConfig?.accessToken ?: throw IllegalStateException("VK-token doesn't initialized"))
        .build()

}

@Module
interface PostPublishingInteractorBinderModule {

    @Binds
    fun bindPostPublishingInteractor_to_Impl(postPublishingInteractorImpl: PostPublishingInteractorImpl): PostPublishingInteractor

}