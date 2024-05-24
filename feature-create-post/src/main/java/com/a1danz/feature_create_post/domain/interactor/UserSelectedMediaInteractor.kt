package com.a1danz.feature_create_post.domain.interactor

import android.content.Context
import android.net.Uri
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.presentation.model.PostPlaceDetailInfoUiModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import java.io.File

interface UserSelectedMediaInteractor {
    fun getTgConfig(): TgConfig?
    fun getVkConfig(): VkConfig?
    suspend fun convertUriToFile(uri: Uri, context: Context): File
    suspend fun getPostPlaceDetailInfoUiModels(postPlaces: List<PostPlaceType>): List<PostPlaceDetailInfoUiModel>
}