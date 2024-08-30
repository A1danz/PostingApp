package com.a1danz.feature_telegram_publisher.domain.di

import com.a1danz.feature_telegram_publisher.di.qualifier.BotTokenQualifier
import com.a1danz.feature_telegram_publisher.domain.client.ITelegramClientWrapper
import com.a1danz.feature_telegram_publisher.domain.client.TelegramClientWrapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.generics.TelegramClient

@Module(includes = [
    TelegramWrapperBinder::class
])
class DomainModule {
    @Provides
    fun provideTelegramClient(@BotTokenQualifier botToken: String): TelegramClient = OkHttpTelegramClient(botToken)
}

@Module
interface TelegramWrapperBinder {

    @Binds
    fun bindTgClientWrapper_to_Impl(telegramClientWrapper: TelegramClientWrapper): ITelegramClientWrapper
}