package com.a1danz.feature_create_post.domain.interactor

import android.content.Context
import android.net.Uri
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import java.io.File

interface UserSelectedMediaInteractor {
    fun getTgConfig(): TgConfig?
    fun getVkConfig(): VkConfig?
    suspend fun convertUriToFile(uri: Uri, context: Context): File
}