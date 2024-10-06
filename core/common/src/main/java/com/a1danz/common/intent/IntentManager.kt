package com.a1danz.common.intent

import android.content.Intent

interface IntentManager {
    fun getTelegramIntent(user: String): Intent?
}