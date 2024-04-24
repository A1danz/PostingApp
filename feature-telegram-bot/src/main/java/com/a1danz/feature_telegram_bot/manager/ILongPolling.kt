package com.a1danz.feature_telegram_bot.manager

import com.a1danz.feature_telegram_bot.long_polling_bot.PostingLongPollingBot

interface ILongPolling {
    fun registerBot()

    fun unregisterBot()
}