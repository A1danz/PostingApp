package com.a1danz.common.ext

import android.content.Context
import com.a1danz.common.presentation.model.Text


fun Text.getString(context: Context): String {
    return when(this) {
        is Text.Simple -> {
            this.string
        }
        is Text.Resource -> {
            context.getString(this.stringRes)
        }
    }
}