package com.a1danz.common.config

object DeeplinkManager {
    private const val TG_USER_LINK = "tg://resolve?domain="
    private const val TG_HTTP_USER_LINK = "https://t.me/"

    fun resolveTgUserDeeplink(username: String): String {
        return TG_USER_LINK + username
    }

    fun resolveTgUserHttpDeeplink(username: String): String {
        return TG_HTTP_USER_LINK + username
    }
}