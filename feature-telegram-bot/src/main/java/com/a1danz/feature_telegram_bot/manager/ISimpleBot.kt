package com.a1danz.feature_telegram_bot.manager

interface ISimpleBot {
    fun sendMessage(chatId: String, message: String)
}