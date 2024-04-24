package com.a1danz.feature_telegram_bot.bot

interface IPostingBot {
    fun sendMessage(chatId: Int, message: String)
}