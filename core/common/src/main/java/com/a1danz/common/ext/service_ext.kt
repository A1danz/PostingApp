package com.a1danz.common.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyTextToClipboard(label: String, text: String) {
    (getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.let { clipboardManager ->
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText(label, text)
        )
    }
}