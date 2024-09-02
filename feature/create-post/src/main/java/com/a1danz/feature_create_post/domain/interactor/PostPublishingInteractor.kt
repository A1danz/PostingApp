package com.a1danz.feature_create_post.domain.interactor

import android.content.Context
import android.net.Uri
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import java.io.File

interface PostPublishingInteractor {
    fun getPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel?
    suspend fun startPublishing(postPublishers: List<PostPublisher>, postModel: PostModel)
    suspend fun convertUriToFile(uri: Uri, context: Context): File
    suspend fun savePostToDatabase(postModel: PostModel, postPlaces: List<PostPlaceType>)
}