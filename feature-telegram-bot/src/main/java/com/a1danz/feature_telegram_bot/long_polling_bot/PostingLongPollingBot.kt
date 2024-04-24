package com.a1danz.feature_telegram_bot.long_polling_bot

import kotlinx.coroutines.flow.StateFlow
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update

class PostingLongPollingBot: LongPollingSingleThreadUpdateConsumer {
    override fun consume(update: Update?) {
        println("HANDLED UPDATE: $update")
    }
}