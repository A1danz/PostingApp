package com.a1danz.feature_create_post.domain.interactor.impl

import android.content.Context
import android.net.Uri
import com.a1danz.common.core.utils.toFile
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.factory.PostPlacesDetailInfoFactory
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.presentation.model.PostPlaceDetailInfoUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class UserSelectedMediaInteractorImpl @Inject constructor(
    private val userConfig: Config,
    private val dispatcher: CoroutineDispatcher,
    private val factory: PostPlacesDetailInfoFactory
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

    override suspend fun getPostPlaceDetailInfoUiModels(postPlaces: List<PostPlaceType>): List<PostPlaceDetailInfoUiModel> {
        return withContext(dispatcher) {
            val result = mutableListOf<PostPlaceDetailInfoUiModel>()
            postPlaces.forEach {
                result.addAll(factory.getPostPlaceDetailInfoModels(it))
            }

            result
        }
    }
}