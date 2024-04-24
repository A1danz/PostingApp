package com.a1danz.feature_telegram_bot.bot.impl

import com.a1danz.feature_telegram_bot.bot.IPostingBot
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.generics.TelegramClient
import javax.inject.Inject

class PostingBot(
    private val telegramClient: TelegramClient
) : IPostingBot {

    override fun sendMessage(chatId: Int, message: String) {
        telegramClient.execute(SendMessage(chatId.toString(), message))
    }
}