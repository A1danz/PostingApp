package com.a1danz.common.presentation.model

import androidx.annotation.StringRes

sealed class Text {
    class Resource(@StringRes val stringRes: Int) : Text()
    class Simple(val string: String) : Text()
}