package com.a1danz.feature_telegram_bot.manager.impl

import com.a1danz.feature_telegram_bot.bot.IPostingBot
import com.a1danz.feature_telegram_bot.bot.impl.PostingBot
import com.a1danz.feature_telegram_bot.long_polling_bot.PostingLongPollingBot
import com.a1danz.feature_telegram_bot.manager.ILongPolling
import com.a1danz.feature_telegram_bot.manager.ISimpleBot
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import javax.inject.Inject

class TelegramBotManager @Inject constructor (
    private val botsApplication: TelegramBotsLongPollingApplication,
    private val botToken: String,
    private val postingBot: IPostingBot
) : ILongPolling, ISimpleBot {
    override fun registerBot() {
        botsApplication.registerBot(botToken, PostingLongPollingBot())
    }

    override fun unregisterBot() {
        botsApplication.unregisterBot(botToken)
    }

    override fun sendMessage(chatId: String, message: String) {
        postingBot.sendMessage(chatId.toInt(), message)
    }
}