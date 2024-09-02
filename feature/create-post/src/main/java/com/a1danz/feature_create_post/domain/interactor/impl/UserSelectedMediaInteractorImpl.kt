package com.a1danz.feature_create_post.domain.interactor.impl

import android.content.Context
import android.net.Uri
import com.a1danz.common.core.utils.toFile
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class UserSelectedMediaInteractorImpl @Inject constructor(
    private val userConfig: Config,
    private val dispatcher: CoroutineDispatcher
) : UserSelectedMediaInteractor {
    override fun getTgConfig(): TgConfig? {
        return userConfig.tgConfig
    }

    override fun getVkConfig(): VkConfig? {
        return userConfig.vkConfig
    }

    override suspend fun convertUriToFile(uri: Uri, context: Context): File {
        return withContext(dispatcher) {
            uri.toFile(context)
        }
    }
}