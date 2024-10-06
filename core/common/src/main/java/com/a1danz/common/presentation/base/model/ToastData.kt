package com.a1danz.common.presentation.base.model

import android.widget.Toast

data class ToastData(
    val text: String,
    val duration: Int = Toast.LENGTH_SHORT
)