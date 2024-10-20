package com.a1danz.feature_create_post.domain.interactor.impl

import android.content.Context
import android.net.Uri
import com.a1danz.common.utils.toFile
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class UserSelectedMediaInteractorImpl @Inject constructor(
    private val user: User,
    private val dispatcher: CoroutineDispatcher
) : UserSelectedMediaInteractor {
    override fun getTgConfig(): TgConfig? {
        return user.config.tgConfig
    }

    override fun getVkConfig(): VkConfig? {
        return user.config.vkConfig
    }

    override suspend fun convertUriToFile(uri: Uri, context: Context): File {
        return withContext(dispatcher) {
            uri.toFile(context)
        }
    }
}