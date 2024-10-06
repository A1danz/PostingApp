package com.a1danz.common.intent.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.a1danz.common.config.DeeplinkManager
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.intent.IntentManager
import javax.inject.Inject

@ApplicationScope
class IntentManagerImpl @Inject constructor(
    private val context: Context,
) : IntentManager {

    override fun getTelegramIntent(user: String): Intent? {
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse(DeeplinkManager.resolveTgUserDeeplink(user))
        ).let { tgIntent ->
            if (tgIntent.resolveActivity(context.packageManager) != null) {
                tgIntent
            } else {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(DeeplinkManager.resolveTgUserHttpDeeplink(user)),
                ).let { tgHttpIntent ->
                    if (tgHttpIntent.resolveActivity(context.packageManager) != null) {
                        tgHttpIntent
                    } else {
                        null
                    }
                }
            }
        }
    }
}