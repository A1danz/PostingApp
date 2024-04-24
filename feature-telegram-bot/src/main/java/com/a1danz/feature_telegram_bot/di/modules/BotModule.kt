package com.a1danz.feature_telegram_bot.di.modules

import com.a1danz.feature_telegram_bot.bot.IPostingBot
import com.a1danz.feature_telegram_bot.bot.impl.PostingBot
import com.a1danz.feature_telegram_bot.di.qualifiers.ApplicationUserId
import com.a1danz.feature_telegram_bot.di.qualifiers.BotToken
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import org.telegram.telegrambots.meta.generics.TelegramClient

@Module
class BotModule {
    @Provides
    fun provideBotsApplication() = TelegramBotsLongPollingApplication()

    @Provides
    @BotToken
    fun provideBotToken(): String = "tg_bot_key"

    @Provides
    fun provideTelegramClient(botToken: String): TelegramClient = OkHttpTelegramClient(botToken)

    @Provides
    fun providePostingBot(telegramClient: TelegramClient): IPostingBot = PostingBot(telegramClient)

}
