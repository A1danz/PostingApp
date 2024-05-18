package com.a1danz.feature_telegram_publisher.di

import com.a1danz.feature_telegram_publisher.di.qualifier.BotTokenQualifier
import com.a1danz.feature_telegram_publisher.domain.TelegramPublisher
import com.a1danz.feature_telegram_publisher.domain.di.DomainModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    DomainModule::class
])
interface TelegramPublisherComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun botToken(@BotTokenQualifier botToken: String): Builder

        fun build(): TelegramPublisherComponent
    }

    fun telegramPublisherFactory(): TelegramPublisher.Factory
}